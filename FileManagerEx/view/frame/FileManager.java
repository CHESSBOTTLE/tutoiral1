package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import base.comp.BaseFrame;
import base.comp.BasePanel;

public class FileManager extends BaseFrame {

	private BasePanel jP1;
	private BasePanel jP2;
	private BasePanel jP3;
	JPanel[] inJP = new JPanel[2];
	JList jL1;
	JTable jT;
	DefaultTableModel dTM;
	JPopupMenu jPop = new JPopupMenu();
	JLabel jL2;
	String name = null;
	File f = null;
	FileInputStream fi = null;
	FileOutputStream fo = null;
	JMenuItem menuitem[] = new JMenuItem[4];
	String column[] = { "Name", "Size", "Modified" };
	JScrollPane jSP[] = new JScrollPane[2];
	String lot[] = { BorderLayout.WEST, BorderLayout.CENTER, BorderLayout.SOUTH };
	JComboBox jCB;
	String path = "C:\\";
	String select = "C:\\";
	int cnt = 0, check = 0;

	public FileManager() {
		super.setFrame(750, 500, "C:\\");
	}

	@Override
	public void setComp() {
		// TODO Auto-generated method stub
		jP1 = new BasePanel(150, 171);
		jP2 = new BasePanel();
		jP3 = new BasePanel();

		jSP[0] = new JScrollPane(jL1 = new JList());
		jP1.add(jSP[0]);
		dTM = new DefaultTableModel(null, column) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		jSP[1] = new JScrollPane(jT = new JTable(dTM));
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jT.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		jT.getTableHeader().setDefaultRenderer(renderer);
		for (int i = 0; i < jT.getColumnCount(); i++) {
			jT.getTableHeader().setReorderingAllowed(false);
			jT.getTableHeader().setResizingAllowed(false);
			jT.getColumnModel().getColumn(i).setResizable(false);
		}
		jT.getColumnModel().getColumn(0).setMinWidth(180);
		jT.getColumnModel().getColumn(2).setMinWidth(250);
		jPop.add(menuitem[0] = new JMenuItem("Show Item in the Folder"));
		jPop.addSeparator();
		jPop.add(menuitem[1] = new JMenuItem("Copy"));
		jPop.add(menuitem[2] = new JMenuItem("Paste"));
		jPop.addSeparator();
		jPop.add(menuitem[3] = new JMenuItem("Delete"));
		jT.add(jPop);
		jSP[1].setPreferredSize(new Dimension(580, 100));
		jP2.add(jSP[1]);
		for (int i = 0; i < 2; i++)
			jP3.add(inJP[i] = new JPanel(new FlowLayout(FlowLayout.LEFT)));
		inJP[1].setLayout(new FlowLayout(FlowLayout.RIGHT));
		inJP[0].add(jL2 = new JLabel("File Explorer"));
		inJP[1].add(jCB = new JComboBox());
		jCB.addItem("English");
		jCB.addItem("한국어");
	}

	@Override
	public void setAction() {
		// TODO Auto-generated method stub
		jL1.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					if (jL1.getSelectedValue().equals("..")) {
						cnt--;
						String road[] = path.split("\\\\");
						path = "";
						for (int i = 0; i < road.length; i++) {
							if (i + 1 != road.length)
								path += road[i] + "\\";
						}
						settitle(path);
						setList();
					} else {
						cnt++;
						path += jL1.getSelectedValue() + "\\";
						select = jL1.getSelectedValue() + "\\";
						settitle(path);
						setList();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block

				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		jCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem().equals("한국어")) {
					menuitem[0].setText("파일열어보기");
					menuitem[1].setText("복사");
					menuitem[2].setText("붙여넣기");
					menuitem[3].setText("삭제");
					column[0] = "파일 이름";
					column[1] = "파일 크기";
					column[2] = "파일 마지막 수정 날짜";
					dTM.setColumnIdentifiers(column);
					jL2.setText("파일 탐색기");
				} else {
					menuitem[0].setText("Show Item in the Folder");
					menuitem[1].setText("Copy");
					menuitem[2].setText("Paste");
					menuitem[3].setText("Delete");
					column[0] = "name";
					column[1] = "size";
					column[2] = "Modified";
					dTM.setColumnIdentifiers(column);
					jL2.setText("File Explorer");
				}
				
			}
		});
		
		jT.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getButton() == 3) {
					if (check == 0)
						menuitem[2].setEnabled(false);
					else
						menuitem[2].setEnabled(true);
					if (jT.getSelectedRowCount() == 1) {
						int row = jT.rowAtPoint(e.getPoint());
						jT.setRowSelectionInterval(row, row);
						jPop.show(jT, e.getX(), e.getY());
					}
				}
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});
		for (int i = 0; i < 4; i++) {
			menuitem[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource().equals(menuitem[0])) {
						try {
							Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + path
									+ jT.getValueAt(jT.getSelectedRow(), 0));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					if (e.getSource().equals(menuitem[1])) {
						f = new File(path + jT.getValueAt(jT.getSelectedRow(), 0));
						name = jT.getValueAt(jT.getSelectedRow(), 0).toString();
						check = 1;
						// try {
						// fi = new FileInputStream(f);
						// } catch (Exception e1) {
						// e1.printStackTrace();
						// }
						setList();
					}
					if (e.getSource().equals(menuitem[2])) {
						try {
							int c;
							String pre_name;
							do {
								pre_name = name;
								for (int i = 0; i < jT.getRowCount(); i++) {
									if (jT.getValueAt(i, 0).toString().equals(name)) {
										String[] tmp = name.split("\\.");
										tmp[0] += " - 복사본";
										name = tmp[0] + "." + tmp[1];
									}
								}
							} while (!pre_name.equals(name));
							fi = new FileInputStream(f);
							fo = new FileOutputStream(new File(path + name));
							while ((c = fi.read()) != -1) {
								fo.write(c);
							}
							fi.close();
							fo.close();
							setList();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					if (e.getSource().equals(menuitem[3])) {
						f = new File(path + jT.getValueAt(jT.getSelectedRow(), 0));
						f.delete();
						setList();
					}
				}
			});
		}
	}

	@Override
	public void close() {}

	@Override
	public void setDesign() {
		// TODO Auto-generated method stub
		this.add(jP1, BorderLayout.WEST);
		this.add(jP2, BorderLayout.CENTER);
		this.add(jP3, BorderLayout.SOUTH);

		jP1.setBorder(new EmptyBorder(10, 10, 10, 10));
		jP1.setLayout(new GridLayout(0, 1));
		jP2.setBorder(new EmptyBorder(8, 0, 10, 0));
		jP3.setLayout(new GridLayout(0, 2));
		jP3.setBorder(new LineBorder(Color.black, 1));
	}

	@Override
	public void setList() {
		// TODO Auto-generated constructor stub
		File c = new File(path), files[] = c.listFiles();
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return !pathname.isFile();
			}
		};
		JOptionPane.showMessageDialog(null, path + "=" + !c.isFile());
		files = c.listFiles(filter);
		String filename[] = new String[files.length + 1];
		Object rowdata[] = new Object[3];
		if (cnt != 0)
			filename[0] = "..";
		for (int i = cnt == 0 ? 0 : 1; i < files.length; i++) {
			if (files[i].toString().contains("$") || files[i].toString().contains("Recovery")
					|| files[i].toString().contains("System") || files[i].toString().contains("Temp")
					|| files[i].toString().contains("PerfLogs"))
				continue;

			filename[i] = files[i].toString().replace(path, "");
		}
		jL1.removeAll();
		jL1.setListData(filename);
		filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return !pathname.isDirectory();
			}
		};
		dTM.setRowCount(0);
		files = c.listFiles(filter);
		for (int i = 0; i < files.length; i++) {
			int size = (int) ((files[i].length() / 1024.0) * 100);
			rowdata[1] = files[i].length() > 1024 ? size / 100.0 + "KB" : files[i].length() + "B";
			rowdata[0] = files[i].toString().replace(path, "");
			SimpleDateFormat date = new SimpleDateFormat("d/M/yyyy HH:mm:ss");
			String strDate = date.format(files[i].lastModified());
			rowdata[2] = strDate;
			dTM.addRow(rowdata);
		}
		if (dTM.getRowCount() > 24) {
			jSP[1].setPreferredSize(new Dimension(580, 24 * 16 + 23));
		} else
			jSP[1].setPreferredSize(new Dimension(580, dTM.getRowCount() * jT.getRowHeight() + 23));
		jP2.revalidate();
		jP2.repaint();
		jT.setRowSelectionInterval(0, 0);
	}
}