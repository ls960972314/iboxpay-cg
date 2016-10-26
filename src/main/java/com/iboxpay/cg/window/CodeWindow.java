package com.iboxpay.cg.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.iboxpay.cg.generate.CodeGenerate;
import com.iboxpay.cg.pojo.CreateFileProperty;
import com.iboxpay.cg.util.CodeResourceUtil;
import com.iboxpay.cg.util.ReadDBUtil;

/**
 * 
 * @author dumengchao
 */
public class CodeWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	String keyType[] = {"sequence","uuid", "identity" };
	String moduleName[] = { "core","web"};
	String dbTypeArray[] = { "oracle","mysql","postgresql", "sqlserver"};
	private CreateFileProperty createFileProperty = null;
	JPanel jp = null;
	JLabel sequence_lb = null;
	JTextField sequence_fld = null;
	public CodeWindow() {
		jp = new JPanel();
		createFileProperty = new CreateFileProperty();
		setContentPane(jp);
		jp.setLayout(new GridLayout(14, 2));
		JLabel infolbl = new JLabel("提示:");
		Font font = new Font("楷体_GB2312", Font.BOLD, 15);
		infolbl.setFont(font);
		final JLabel showlbl = new JLabel();
		showlbl.setForeground(Color.red);
		showlbl.setText("【相同代码会覆盖】");
		JLabel packagebl = new JLabel("生成到模块(暂时无用)");
		packagebl.setForeground(Color.red);
		final JComboBox packagefld = new JComboBox(moduleName);
		JLabel dbtypeLbl = new JLabel("db类型");
		final JComboBox dbtypeCom = new JComboBox(dbTypeArray);
		JLabel entitylbl = new JLabel("实体类名");
		final JTextField entityfld = new JTextField();
		JLabel tablejbl = new JLabel("表名");
		final JTextField tablefld = new JTextField(20);
		JLabel tablekeyjbl = new JLabel("主键类型");
		final JComboBox tablekeyfld = new JComboBox(keyType);
		sequence_lb = new JLabel("oracle序列");
		sequence_fld = new JTextField(20);
		JLabel titlelbl = new JLabel("功能描述");
		final JTextField titlefld = new JTextField();
		final JCheckBox actionButton = new JCheckBox("Controller");
		 actionButton.setSelected(true);
		final JCheckBox mapperButton = new JCheckBox("Mapper");
		mapperButton.setSelected(true);
		final JCheckBox serviceIButton = new JCheckBox("Service");
		serviceIButton.setSelected(true);
		final JCheckBox serviceImplButton = new JCheckBox("ServiceImpl");
		serviceImplButton.setSelected(true);
		final JCheckBox entityButton = new JCheckBox("domain");
		entityButton.setSelected(true);
		final JCheckBox domainWithoutAnnotation = new JCheckBox("VO");
		domainWithoutAnnotation.setSelected(true);
//		 final JCheckBox daoButton = new JCheckBox("dao");
//		 daoButton.setSelected(true);
//		 final JCheckBox daoImplButton = new JCheckBox("daoImpl");
//		 daoImplButton.setSelected(true);
		jp.add(infolbl);
		jp.add(showlbl);
		jp.add(packagebl);
		jp.add(packagefld);
		jp.add(dbtypeLbl);
		jp.add(dbtypeCom);
		jp.add(entitylbl);
		jp.add(entityfld);
		jp.add(tablejbl);
		jp.add(tablefld);
		jp.add(tablekeyjbl);
		jp.add(tablekeyfld);
		jp.add(sequence_lb);
		jp.add(sequence_fld);
		jp.add(titlelbl);
		jp.add(titlefld);
		jp.add(actionButton);
		jp.add(domainWithoutAnnotation);
		jp.add(mapperButton);
		jp.add(serviceIButton);
		jp.add(serviceImplButton);
		// jp.add(daoButton);
		// jp.add(daoImplButton);
		jp.add(entityButton);

		JButton confirmbtn = new JButton("生成");
		JButton extbtn = new JButton("退出");

		confirmbtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String modelPackage = (String) packagefld.getSelectedItem();
				if (!"".equals(modelPackage)) {
					createFileProperty.setEntityPackage(modelPackage.trim());
				} else {
					showlbl.setForeground(Color.red);
					showlbl.setText("模块不能为空");
					return;
				}
				String dbtypeComItem = (String) dbtypeCom.getSelectedItem();
				
				if (!"".equals(dbtypeComItem)) {
					createFileProperty.setDbType(dbtypeComItem.trim());
				} else {
					showlbl.setForeground(Color.red);
					showlbl.setText("数据库类型不能为空");
					return;
				}
				
				if (!"".equals(entityfld.getText())) {
					createFileProperty.setEntityName(entityfld.getText().trim());
				} else {
					showlbl.setForeground(Color.red);
					showlbl.setText("实体类名不能为空！");
					return;
				}
				if (!"".equals(titlefld.getText())) {
					createFileProperty.setFtlDescription(titlefld.getText().trim());
				} else {
					showlbl.setForeground(Color.red);
					showlbl.setText("描述不能为空！");
					return;
				}
				if (!"".equals(tablefld.getText())) {
					createFileProperty.setTableName(tablefld.getText().trim());
				} else {
					showlbl.setForeground(Color.red);
					showlbl.setText("表名不能为空！");
					return;
				}
				String temp = (String) tablekeyfld.getSelectedItem();
				if ("sequence".equals(temp)) {
					if (!"".equals(sequence_fld.getText())) {
						createFileProperty.setSequenceCode(sequence_fld.getText().trim());
					} else {
						showlbl.setForeground(Color.red);
						showlbl.setText("主键生成策略为sequence时，序列号不能为空！");
						return;
					}
				} 
				createFileProperty.setIdStategy(temp);
				if (mapperButton.isSelected())
					createFileProperty.setMapperFlag(true);
				if (actionButton.isSelected())
					createFileProperty.setActionFlag(true);
				if (domainWithoutAnnotation.isSelected())
					createFileProperty.setVoFlag(true);
				if (serviceIButton.isSelected())
					createFileProperty.setServiceIFlag(true);
				if (serviceImplButton.isSelected())
					createFileProperty.setServiceImplFlag(true);
				if (entityButton.isSelected())
					createFileProperty.setEntityFlag(true);
				try {
					ReadDBUtil dbUtil = new ReadDBUtil();
					CodeResourceUtil.initDBParams(createFileProperty);

					boolean tableExists = dbUtil.checkTableExist(createFileProperty);
					if (!tableExists) {

						showlbl.setForeground(Color.red);
						showlbl.setText((new StringBuilder("表[")).append(createFileProperty.getTableName()).append("] 不存在").toString());
						return;
					}

//					boolean pkExists = dbUtil.checkTablePKIsExist(createFileProperty.getTableName());
//					if (!pkExists) {
//						showlbl.setForeground(Color.red);
//						showlbl.setText((new StringBuilder("表[")).append(createFileProperty.getTableName()).append("] 主键 不存在").toString());
//						return;
//					}

					CodeGenerate cg = new CodeGenerate(createFileProperty);
					cg.generateToFile();
					showlbl.setForeground(Color.blue);
					showlbl.setText((new StringBuilder("成功生成增删改查！")).append(createFileProperty.getFtlDescription()).toString());
				} catch (Exception e1) {
					e1.printStackTrace();
					showlbl.setForeground(Color.red);
					showlbl.setText(e1.getMessage());
				}
			}
		});
		extbtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(0);
			}

		});
		jp.add(confirmbtn);
		jp.add(extbtn);
		setTitle("cg");
		setVisible(true);
		setDefaultCloseOperation(3);
		setSize(new Dimension(500, 400));
		setResizable(false);
		setLocationRelativeTo(getOwner());
	}

	public static void main(String args[]) {
		try {
			(new CodeWindow()).pack();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
