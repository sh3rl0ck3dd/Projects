
package imagepro;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import javax.swing.colorchooser.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

public class ImagePro extends JFrame implements ActionListener, ItemListener,
		ChangeListener, MouseMotionListener, MouseListener {
	JColorChooser tcc1, tcc2;
	JFileChooser fcc;
	String filename = "";
	String password = "";
	double screenw = 800;
	double screenh = 600;
	BufferedImage bufferimagedisplay = new BufferedImage(500, 500,
			BufferedImage.TYPE_INT_RGB);
	BufferedImage bufferimagedisplay2 = new BufferedImage(500, 500,
			BufferedImage.TYPE_INT_RGB);
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension screenSize = toolkit.getScreenSize();
	// Image imagedisplay2;
	// Runtime runtimeforgarbagecollection = Runtime.getRuntime();

	int maximageframesize = ((int) screenSize.getWidth() * 2) / 3;
	int width = 400;
	int height = 400;
	int widthbuffer = 0;
	int heightbuffer = 0;
	int[][][] colouray;
	int rgbs[];
	int rgbsbuffer[];
	int rgbsundoarray[];
	int widthundo = 0;
	int heightundo = 0;
	int regions = 5;
	int encryptationcycles = 0;
	boolean imageopen = false;
	boolean dialogopen = false;
	boolean otherframeopen = false;
	Color colorsel1;
	Color colorsel2;

	int brightnessincrement = 10;
	int brightnessvalue = 0;

	int initmousex = 0;
	int initmousey = 0;
	int finamousex = 0;
	int finamousey = 0;
	int nowmousex = 0;
	int nowmousey = 0;
	int rectix = 0;
	int rectiy = 0;
	int rectfx = 0;
	int rectfy = 0;
	double imagescaleratio = 1.0;

	int limitingframewidth = 0;

	boolean selrectformed = false;
	boolean selectedrectdragged = false;
	int dragstartcoordinatex = 0;
	int dragstartcoordinatey = 0;
	int dragendcoordinatex = 0;
	int dragendcoordinatey = 0;

	String action = "";

	JLabel jlabel1, jlabel2, jlabel3, jlabel4;

	JFrame imageframe;

	// the dialog box for brightness adjustment
	JDialog brightnesset;
	JButton submitbrightness;
	JSlider adjustbrightness;

	// the dialog box for region adjustment
	JDialog regionset;
	JButton submitregion;
	JSlider adjustregion;

	// the dialog box for size adjustment
	JDialog adjustsize;
	JTextField sizex;
	JTextField sizey;
	JButton submitsize;
	JLabel xsize;
	JLabel ysize;
	JCheckBox maintainaspect;

	// the dialog box for encryptation password
	JDialog givepassword;
	JPasswordField passwordf;
	JTextField numberofcycles;
	JButton submitpassword;

	// the dialog box for arbitrary rotation
	JDialog giveangle;
	JTextField rotationangle;
	JButton submitrotationangle;

	// the dialog box for colourcontrast adjustment
	JDialog colourcontrastset;
	JButton submitcolourcontrast;
	JSlider adjustcolourcontrast;

	// the dialog box for contrast adjustment
	JDialog contrastset;
	JButton submitcontrast;
	JSlider adjustcontrast;
	// the dialog box for contrast stretching
	JDialog stretchcontrast;
	JTextField rangecont;
	JButton submitrange;

	// the dialog box for color swapping
	JDialog entercolor;

	// the dialog box for gamma value
	JDialog gammabasis;
	JTextField basisgam;

	static JFrame frame;

	private static void createAndShowGUI() {

		frame = new ImagePro();
		frame.setLayout(new FlowLayout());
		// frame.setSize(500,500);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		Image frmic = (new ImageIcon("1.jpg")).getImage();
		frame.setIconImage(frmic);
		// frame.setBounds(0,0,1024,700);
		// frame.setBackground(Color.BLACK);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}
		// This method should be used when an application thread needs to update
		// the GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	public ImagePro() {
		super("ImagePro");

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JMenuBar mbar = new JMenuBar();
		setJMenuBar(mbar);

		jlabel1 = new JLabel("", new ImageIcon(""), JLabel.LEFT);
		add(jlabel1);

		jlabel1.add(new JSeparator(SwingConstants.VERTICAL));

		jlabel3 = new JLabel("", new ImageIcon(""), JLabel.LEFT);
		jlabel3.addMouseMotionListener(this);
		jlabel3.addMouseListener(this);
		add(jlabel3);

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		JMenuItem open = new JMenuItem("Open");
		file.add(open);
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.ALT_MASK));
		open.addActionListener(this);
		file.addSeparator();
		JMenuItem save = new JMenuItem("Save");
		file.add(save);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		save.addActionListener(this);
		file.addSeparator();
		// JMenuItem saveas = new JMenuItem("Save as");
		// file.add(saveas);
		// saveas.addActionListener(this);
		// file.addSeparator();
		JMenuItem quit = new JMenuItem("Quit");
		file.add(quit);
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.ALT_MASK));
		quit.addActionListener(this);
		mbar.add(file);

		// creating edit menu
		JMenu edit = new JMenu("Edit");
		JMenuItem undo = new JMenuItem("Undo");
		edit.add(undo);
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				ActionEvent.CTRL_MASK));
		undo.addActionListener(this);
		edit.addSeparator();
		JMenuItem cut = new JMenuItem("Cut");
		edit.add(cut);
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));
		cut.addActionListener(this);
		mbar.add(edit);

		// creating sub menu of rotate
		JMenu rotate = new JMenu("Rotate");
		JMenuItem fvertical = new JMenuItem("Flip vertical");
		rotate.add(fvertical);
		fvertical.addActionListener(this);
		rotate.addSeparator();
		JMenuItem fhorizontal = new JMenuItem("Flip horizontal");
		rotate.add(fhorizontal);
		fhorizontal.addActionListener(this);
		rotate.addSeparator();
		JMenuItem transpose = new JMenuItem("Transpose");
		rotate.add(transpose);
		transpose.addActionListener(this);
		rotate.addSeparator();

		// creating sub menu of adjust
		JMenu adjust = new JMenu("Adjust");
		JMenuItem brightness = new JMenuItem("Brightness");
		adjust.add(brightness);
		brightness.addActionListener(this);
		adjust.addSeparator();
		JMenuItem contrast = new JMenuItem("Contrast");
		adjust.add(contrast);
		contrast.addActionListener(this);
		contrast.setToolTipText("enhance the contrast of image by 20%");
		adjust.addSeparator();
		JMenuItem colourcontrast = new JMenuItem("Colour Contrast");
		adjust.add(colourcontrast);
		colourcontrast.addActionListener(this);
		adjust.addSeparator();
		JMenuItem size = new JMenuItem("Size");
		adjust.add(size);
		size.addActionListener(this);

		// creating sub menu of swap color
		JMenu swapcolor = new JMenu("Swap color");
		JMenuItem redandgreen = new JMenuItem("Red and Green");
		swapcolor.add(redandgreen);
		redandgreen.addActionListener(this);
		swapcolor.addSeparator();
		JMenuItem redandblue = new JMenuItem("Red and Blue");
		swapcolor.add(redandblue);
		redandblue.addActionListener(this);
		swapcolor.addSeparator();
		JMenuItem greenandblue = new JMenuItem("Blue and Green");
		swapcolor.add(greenandblue);
		greenandblue.addActionListener(this);

		// creating sub menu of turn rgb
		JMenu turnrgb = new JMenu("Turn RGB to..");
		JMenuItem m0 = new JMenuItem("rgb to rrr..0");
		JMenuItem m1 = new JMenuItem("rgb to rrg..1");
		JMenuItem m2 = new JMenuItem("rgb to rrb..2");
		JMenuItem m3 = new JMenuItem("rgb to rgr..3");
		JMenuItem m4 = new JMenuItem("rgb to rgg..4");
		JMenuItem m5 = new JMenuItem("rgb to rgb..5");
		JMenuItem m6 = new JMenuItem("rgb to rbr..6");
		JMenuItem m7 = new JMenuItem("rgb to rbg..7");
		JMenuItem m8 = new JMenuItem("rgb to rbb..8");
		JMenuItem m9 = new JMenuItem("rgb to grr..9");
		JMenuItem m10 = new JMenuItem("rgb to grg..10");
		JMenuItem m11 = new JMenuItem("rgb to grb..11");
		JMenuItem m12 = new JMenuItem("rgb to ggr..12");
		JMenuItem m13 = new JMenuItem("rgb to ggg..13");
		JMenuItem m14 = new JMenuItem("rgb to ggb..14");
		JMenuItem m15 = new JMenuItem("rgb to gbr..15");
		JMenuItem m16 = new JMenuItem("rgb to gbg..16");
		JMenuItem m17 = new JMenuItem("rgb to gbb..17");
		JMenuItem m18 = new JMenuItem("rgb to brr..18");
		JMenuItem m19 = new JMenuItem("rgb to brg..19");
		JMenuItem m20 = new JMenuItem("rgb to brb..20");
		JMenuItem m21 = new JMenuItem("rgb to bgr..21");
		JMenuItem m22 = new JMenuItem("rgb to bgg..22");
		JMenuItem m23 = new JMenuItem("rgb to bgb..23");
		JMenuItem m24 = new JMenuItem("rgb to bbr..24");
		JMenuItem m25 = new JMenuItem("rgb to bbg..25");
		JMenuItem m26 = new JMenuItem("rgb to bbb..26");
		m0.addActionListener(this);
		m1.addActionListener(this);
		m2.addActionListener(this);
		m3.addActionListener(this);
		m4.addActionListener(this);
		m5.addActionListener(this);
		m6.addActionListener(this);
		m7.addActionListener(this);
		m8.addActionListener(this);
		m9.addActionListener(this);
		m10.addActionListener(this);
		m11.addActionListener(this);
		m12.addActionListener(this);
		m13.addActionListener(this);
		m14.addActionListener(this);
		m15.addActionListener(this);
		m16.addActionListener(this);
		m17.addActionListener(this);
		m18.addActionListener(this);
		m19.addActionListener(this);
		m20.addActionListener(this);
		m21.addActionListener(this);
		m22.addActionListener(this);
		m23.addActionListener(this);
		m24.addActionListener(this);
		m25.addActionListener(this);
		m26.addActionListener(this);
		turnrgb.add(m0);
		turnrgb.addSeparator();
		turnrgb.add(m1);
		turnrgb.addSeparator();
		turnrgb.add(m2);
		turnrgb.addSeparator();
		turnrgb.add(m3);
		turnrgb.addSeparator();
		turnrgb.add(m4);
		turnrgb.addSeparator();
		turnrgb.add(m5);
		turnrgb.addSeparator();
		turnrgb.add(m6);
		turnrgb.addSeparator();
		turnrgb.add(m7);
		turnrgb.addSeparator();
		turnrgb.add(m8);
		turnrgb.addSeparator();
		turnrgb.add(m9);
		turnrgb.addSeparator();
		turnrgb.add(m10);
		turnrgb.addSeparator();
		turnrgb.add(m11);
		turnrgb.addSeparator();
		turnrgb.add(m12);
		turnrgb.addSeparator();
		turnrgb.add(m13);
		turnrgb.addSeparator();
		turnrgb.add(m14);
		turnrgb.addSeparator();
		turnrgb.add(m15);
		turnrgb.addSeparator();
		turnrgb.add(m16);
		turnrgb.addSeparator();
		turnrgb.add(m17);
		turnrgb.addSeparator();
		turnrgb.add(m18);
		turnrgb.addSeparator();
		turnrgb.add(m19);
		turnrgb.addSeparator();
		turnrgb.add(m20);
		turnrgb.addSeparator();
		turnrgb.add(m21);
		turnrgb.addSeparator();
		turnrgb.add(m22);
		turnrgb.addSeparator();
		turnrgb.add(m23);
		turnrgb.addSeparator();
		turnrgb.add(m24);
		turnrgb.addSeparator();
		turnrgb.add(m25);
		turnrgb.addSeparator();
		turnrgb.add(m26);

		// creating image menu
		JMenu image = new JMenu("Image");
		image.add(adjust);
		image.addSeparator();
		JMenuItem show = new JMenuItem("Show Info");
		image.add(show);
		show.addActionListener(this);
		image.addSeparator();
		JMenuItem invert = new JMenuItem("Invert");
		image.add(invert);
		invert.addActionListener(this);
		image.addSeparator();
		JMenuItem black = new JMenuItem("Black & White");
		image.add(black);
		black.addActionListener(this);
		image.addSeparator();
		image.add(rotate);
		image.addSeparator();
		image.add(swapcolor);
		image.addSeparator();
		JMenuItem swapanycolor = new JMenuItem("Swap Any Color");
		image.add(swapanycolor);
		swapanycolor.addActionListener(this);
		image.addSeparator();
		image.add(turnrgb);
		turnrgb.addActionListener(this);
		image.addSeparator();
		JMenuItem crop = new JMenuItem("Crop");
		image.add(crop);
		crop.addActionListener(this);
		image.addSeparator();
		JMenuItem distribute = new JMenuItem("Distribute");
		image.add(distribute);
		distribute.addActionListener(this);
		mbar.add(image);

		// creating menu of math
		JMenu math = new JMenu("Math");

		JMenuItem boolnot = new JMenuItem("NOT");
		math.add(boolnot);
		boolnot.addActionListener(this);
		math.addSeparator();
		JMenuItem log = new JMenuItem("Log");
		math.add(log);
		log.addActionListener(this);
		math.addSeparator();
		JMenuItem square = new JMenuItem("Square");
		math.add(square);
		square.addActionListener(this);
		math.addSeparator();
		JMenuItem reciprocal = new JMenuItem("Reciprocal");
		math.add(reciprocal);
		reciprocal.addActionListener(this);
		mbar.add(math);

		// creating menu of filters
		JMenu filters = new JMenu("Filters");
		JMenuItem median = new JMenuItem("Median");
		filters.add(median);
		median.addActionListener(this);
		filters.addSeparator();
		JMenuItem minimum = new JMenuItem("Minimum");
		filters.add(minimum);
		minimum.addActionListener(this);
		filters.addSeparator();
		JMenuItem maximum = new JMenuItem("Maximum");
		filters.add(maximum);
		maximum.addActionListener(this);
		mbar.add(filters);

		// creating submenu for smooth

		JMenu smooth = new JMenu("Smooth");
		JMenuItem smoothnine = new JMenuItem("Smooth 9x9");
		smooth.add(smoothnine);
		smoothnine.addActionListener(this);
		smooth.addSeparator();
		JMenuItem conservative = new JMenuItem("Conservative");
		smooth.add(conservative);
		conservative.addActionListener(this);

		// creating process menu
		JMenu process = new JMenu("Process");
		process.add(smooth);
		process.addSeparator();
		JMenuItem sharpen = new JMenuItem("Sharpen");
		process.add(sharpen);
		sharpen.addActionListener(this);
		process.addSeparator();
		JMenuItem enhancecontrast = new JMenuItem("Contrast Stretch");
		enhancecontrast.addActionListener(this);
		process.add(enhancecontrast);
		process.addSeparator();
		JMenuItem equalizehistogram = new JMenuItem("Equalize Histogram");
		equalizehistogram.addActionListener(this);
		process.add(equalizehistogram);
		process.addSeparator();
		JMenuItem binary = new JMenuItem("Binary");
		process.add(binary);
		binary.addActionListener(this);
		process.addSeparator();
		JMenuItem findedges = new JMenuItem("Find Edges");
		process.add(findedges);
		findedges.addActionListener(this);
		process.addSeparator();
		JMenuItem emboss = new JMenuItem("Emboss");
		process.add(emboss);
		emboss.addActionListener(this);
		process.addSeparator();
		JMenuItem pencilsketch = new JMenuItem("Pencil Sketch");
		process.add(pencilsketch);
		pencilsketch.addActionListener(this);
		process.addSeparator();
		JMenuItem encrypt = new JMenuItem("Encrypt");
		process.add(encrypt);
		encrypt.addActionListener(this);
		process.addSeparator();
		JMenuItem decrypt = new JMenuItem("Decrypt");
		process.add(decrypt);
		decrypt.addActionListener(this);
		mbar.add(process);

		// creating analyze menu
		JMenu analyze = new JMenu("Analyze");
		JMenuItem histogram = new JMenuItem("Histogram");
		analyze.add(histogram);
		histogram.addActionListener(this);
		mbar.add(analyze);

		jlabel2 = new JLabel("", new ImageIcon(""), JLabel.LEFT);

		ImageIcon i4 = new ImageIcon("1.jpg");
		jlabel4 = new JLabel("", i4, JLabel.LEFT);

		imageframe = new JFrame("Image");
		imageframe.setSize(500, 500);
		imageframe.add(jlabel3);

		brightnesset = new JDialog(frame, "Adjust Brightness");
		brightnesset.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		brightnesset.setLayout(new FlowLayout());
		brightnesset.setSize(300, 100);
		brightnesset.setVisible(false);
		submitbrightness = new JButton("Submit");
		submitbrightness.addActionListener(this);
		submitbrightness.setActionCommand("Submit Brightness");
		adjustbrightness = new JSlider(-50, 50, 0);
		adjustbrightness.setMinorTickSpacing(5);
		adjustbrightness.setMajorTickSpacing(20);
		adjustbrightness.setPaintTicks(true);
		adjustbrightness.setPaintLabels(true);
		brightnesset.add(adjustbrightness);
		brightnesset.add(submitbrightness);

		colourcontrastset = new JDialog(frame, "Adjust Colour Contrast");
		colourcontrastset.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		colourcontrastset.setLayout(new FlowLayout());
		colourcontrastset.setSize(300, 100);
		colourcontrastset.setVisible(false);
		submitcolourcontrast = new JButton("Submit");
		submitcolourcontrast.addActionListener(this);
		submitcolourcontrast.setActionCommand("Submit Colour Contrast");
		adjustcolourcontrast = new JSlider(-50, 50, 0);
		adjustcolourcontrast.setMinorTickSpacing(5);
		adjustcolourcontrast.setMajorTickSpacing(20);
		adjustcolourcontrast.setPaintTicks(true);
		adjustcolourcontrast.setPaintLabels(true);
		colourcontrastset.add(adjustcolourcontrast);
		colourcontrastset.add(submitcolourcontrast);

		contrastset = new JDialog(frame, "Adjust Contrast");
		contrastset.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		contrastset.setLayout(new FlowLayout());
		contrastset.setSize(300, 100);
		contrastset.setVisible(false);
		submitcontrast = new JButton("Submit");
		submitcontrast.addActionListener(this);
		submitcontrast.setActionCommand("Submit Contrast");
		adjustcontrast = new JSlider(-50, 50, 0);
		adjustcontrast.setMinorTickSpacing(5);
		adjustcontrast.setMajorTickSpacing(20);
		adjustcontrast.setPaintTicks(true);
		adjustcontrast.setPaintLabels(true);
		contrastset.add(adjustcontrast);
		contrastset.add(submitcontrast);

		regionset = new JDialog(frame, "Adjust Region");
		regionset.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		regionset.setLayout(new FlowLayout());
		regionset.setSize(300, 100);
		regionset.setVisible(false);
		submitregion = new JButton("Submit");
		submitregion.addActionListener(this);
		submitregion.setActionCommand("Submit Region");
		adjustregion = new JSlider(0, 25, 1);
		adjustregion.setMinorTickSpacing(1);
		adjustregion.setMajorTickSpacing(5);
		adjustregion.setPaintTicks(true);
		adjustregion.setPaintLabels(true);
		regionset.add(adjustregion);
		regionset.add(submitregion);

		adjustsize = new JDialog(frame, "Adjust Size");
		adjustsize.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		adjustsize.setLayout(new FlowLayout());
		adjustsize.setSize(200, 150);
		adjustsize.setVisible(false);
		submitsize = new JButton("Submit");
		submitsize.addActionListener(this);
		submitsize.setActionCommand("Submit Size");
		sizex = new JTextField("", 4);
		sizey = new JTextField("", 4);
		xsize = new JLabel("Width");
		xsize.setSize(80, 10);
		ysize = new JLabel("Height");
		ysize.setSize(80, 10);
		maintainaspect = new JCheckBox("Keep Aspect Ratio", false);
		maintainaspect.setSize(40, 10);
		adjustsize.add(xsize);
		adjustsize.add(sizex);
		adjustsize.add(ysize);
		adjustsize.add(sizey);
		adjustsize.add(maintainaspect);
		adjustsize.add(submitsize);

		givepassword = new JDialog(frame, "Enter Password");
		givepassword.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		givepassword.setLayout(new FlowLayout());
		givepassword.setSize(270, 100);
		givepassword.setVisible(false);
		passwordf = new JPasswordField(10);
		numberofcycles = new JTextField("", 5);
		submitpassword = new JButton("Submit");
		submitpassword.addActionListener(this);
		submitpassword.setActionCommand("Submit Password");
		JLabel jlpass = new JLabel("Password");
		jlpass.setLabelFor(passwordf);
		JLabel jlcyc = new JLabel("cycles");
		jlcyc.setLabelFor(numberofcycles);
		givepassword.add(jlpass);
		givepassword.add(passwordf);
		givepassword.add(jlcyc);
		givepassword.add(numberofcycles);
		givepassword.add(submitpassword);

		giveangle = new JDialog(frame, "Rotation Angle");
		givepassword.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		giveangle.setLayout(new FlowLayout());
		giveangle.setSize(270, 100);
		giveangle.setVisible(false);
		rotationangle = new JTextField("", 5);
		submitrotationangle = new JButton("Submit");
		submitrotationangle.setActionCommand("Submit rotation angle");
		submitrotationangle.addActionListener(this);

		entercolor = new JDialog(frame, "Enter Colors");
		entercolor.setLayout(new FlowLayout());
		entercolor.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JButton submitbothcolor = new JButton("Done");
		submitbothcolor.setActionCommand("both colors submitted");
		entercolor.setSize(500, 700);
		submitbothcolor.addActionListener(this);
		entercolor.add(submitbothcolor);
		tcc1 = new JColorChooser();
		tcc1.getSelectionModel().addChangeListener(this);
		tcc2 = new JColorChooser();
		tcc2.getSelectionModel().addChangeListener(this);
		tcc1.setBorder(BorderFactory.createTitledBorder("Choose Color one "));
		tcc2.setBorder(BorderFactory.createTitledBorder("Choose Color two "));
		AbstractColorChooserPanel[] oldPanels = tcc1.getChooserPanels();
		// Remove panels
		for (int i = 0; i < oldPanels.length; i++) {
			String clsName = oldPanels[i].getClass().getName();
			if (clsName
					.equals("javax.swing.colorchooser.DefaultSwatchChooserPanel")) {
				tcc1.removeChooserPanel(oldPanels[i]);
			} else if (clsName
					.equals("javax.swing.colorchooser.DefaultHSBChooserPanel")) {
				tcc1.removeChooserPanel(oldPanels[i]);
			}
		}
		oldPanels = tcc2.getChooserPanels();
		// Remove panels
		for (int i = 0; i < oldPanels.length; i++) {
			String clsName = oldPanels[i].getClass().getName();
			if (clsName
					.equals("javax.swing.colorchooser.DefaultSwatchChooserPanel")) {
				tcc2.removeChooserPanel(oldPanels[i]);
			} else if (clsName
					.equals("javax.swing.colorchooser.DefaultHSBChooserPanel")) {
				tcc2.removeChooserPanel(oldPanels[i]);
			}
		}
		entercolor.add(tcc1);
		entercolor.add(tcc2);
		entercolor.add(submitbothcolor);

		stretchcontrast = new JDialog(frame, "Enter non-outliers");
		stretchcontrast.setLayout(new FlowLayout());
		stretchcontrast.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		stretchcontrast.setSize(270, 100);
		JButton submitrange = new JButton("Submit");
		submitrange.addActionListener(this);
		submitrange.setActionCommand("stretchcontrast submitted");
		rangecont = new JTextField("1", 5);
		stretchcontrast.add(new JLabel("Non-Outlier % "));
		stretchcontrast.add(rangecont);
		stretchcontrast.add(submitrange);

		gammabasis = new JDialog(frame, "Enter Basis");
		gammabasis.setLayout(new FlowLayout());
		gammabasis.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		gammabasis.setSize(270, 100);
		JButton submitbasis = new JButton("Submit");
		submitbasis.addActionListener(this);
		submitbasis.setActionCommand("gamma basis submitted");
		basisgam = new JTextField("1", 5);
		gammabasis.add(new JLabel("Enter power basis"));
		gammabasis.add(basisgam);
		gammabasis.add(submitbasis);

		fcc = new JFileChooser();

		jlabel1.setBounds(10, 10, 500, 500);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent ae) {

		action = ae.getActionCommand();

		if (action != "Quit" && action != "Open" && imageopen == false) {
			JOptionPane.showMessageDialog(null, "No Image Selected");
			return;
		}

		if (action != "Undo" && imageopen && action != "Open"
				&& action != "Crop") {
			rgbsundoarray = new int[width * height];
			for (int a = 0; a < width * height; a++) {
				rgbsundoarray[a] = rgbs[a];
			}
			widthundo = width;
			heightundo = height;
		}

		if (selrectformed && action != "Crop" && action != "Open"
				&& action != "Save" && action != "Quit") {

			rgbsbuffer = new int[width * height];
			widthbuffer = width;
			heightbuffer = height;
			for (int a = 0; a < width * height; a++) {
				rgbsbuffer[a] = rgbs[a];
			}
			int initialx = (int) (rectix / imagescaleratio);
			int initialy = (int) (rectiy / imagescaleratio);
			int finalx = (int) (rectfx / imagescaleratio);
			int finaly = (int) (rectfy / imagescaleratio);
			int w = finalx - initialx + 1;
			int h = finaly - initialy + 1;
			BufferedImage buffer1 = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			BufferedImage buffer2 = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
			buffer1.setRGB(0, 0, width, height, rgbs, 0, width);
			buffer2 = buffer1.getSubimage(initialx, initialy, w, h);
			width = w;
			height = h;
			rgbs = new int[w * h];
			buffer2.getRGB(0, 0, width, height, rgbs, 0, width);

		}

		if (action == "Histogram") {
			BufferedImage bufferimage3 = new BufferedImage(532, 500,
					BufferedImage.TYPE_INT_RGB);
			Graphics grahist = bufferimage3.createGraphics();
			drawhist(grahist);
			Image hist = toImage(bufferimage3);
			jlabel2.setIcon(new ImageIcon(hist));

			JFrame histogramframe = new JFrame("Histogram");
			histogramframe.setSize(650, 550);
			histogramframe.add(jlabel2);
			histogramframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			histogramframe.setVisible(true);
			histogramframe.pack();
			dialogopen = true;
		}

		if (action == "Show Info") {
			BufferedImage bufferimage3 = new BufferedImage(532, 500,
					BufferedImage.TYPE_INT_RGB);
			Graphics grahist = bufferimage3.createGraphics();
			String file = "";

			for (int a = filename.length() - 1; a >= 0; a--) {
				char x = 'b';
				x = filename.charAt(a);
				if ((x + x) != 184) {
					file = x + file;
				} else
					break;
			}
			filename = file;
			showinfo(grahist);
			Image info = toImage(bufferimage3);
			jlabel4.setIcon(new ImageIcon(info));
			JFrame infoframe = new JFrame("Information");
			infoframe.setSize(650, 550);
			infoframe.add(jlabel4);
			infoframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			infoframe.setVisible(true);
			infoframe.pack();

		}

		if (action == "Open") {
			fcc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = fcc.showDialog(ImagePro.this, "Open");
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File filex = fcc.getSelectedFile();
				BufferedImage buffer = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				buffer = (BufferedImage) loadimage(filex);

				filename = "" + filex;

				width = buffer.getWidth();
				height = buffer.getHeight();
				rgbs = new int[width * height];
				buffer.getRGB(0, 0, width, height, rgbs, 0, width);

				// imagedisplay = toImage(compressImage(bufferimage1 , 400.0 ,
				// 400.0));
				// i1=new ImageIcon(imagedisplay);
				// jlabel1.setIcon(i1);

				// jlabel1.setBounds(10,10,500,500);
				int brightnessincrement = 10;
				int brightnessvalue = 0;
				imageopen = true;
			}
			if (returnVal == JFileChooser.CANCEL_OPTION) {
			}
		}

		if (action == "Save") {
			fcc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = fcc.showDialog(ImagePro.this, "Save");
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				BufferedImage bufferimagesave = new BufferedImage(width,
						height, BufferedImage.TYPE_INT_RGB);
				bufferimagesave.setRGB(0, 0, width, height, rgbs, 0, width);
				File filex = fcc.getSelectedFile();
				saveimage(bufferimagesave, filex);

				// This is where a real application would save the file.
			}
		}
		if (action == "Quit") {
			System.exit(0);
		}
		if (action == "Invert") {
			invert();
		}// 1
		if (action == "Black & White") {
			blackandwhite();
		}// 2
		if (action == "Smooth 9x9") {
			smoothing();
		}// 3
		if (action == "Weighted Smooth 9x9") {
			weightedsmoothing();
		}// 4
		if (action == "Median") {
			filtermedian();
		}// 5
		if (action == "Threshhold Average") {
			threshholdaveraging();
		}// 6
		if (action == "Conservative") {
			conservative();
		}// 7
		if (action == "Find Edges") {
			findedgessuperb();
		}// 8
		if (action == "Sharpen") {
			sharpen2();
		}// 9
			// if(action=="SquareRoot"){squareroot();}//10
		if (action == "Log") {
			log();
		}// 11
		if (action == "Square") {
			square();
		}// 12
		if (action == "Reciprocal") {
			reciprocal();
		}// 13
		if (action == "Flip vertical") {
			flipvertical();
		}// 14
		if (action == "Flip horizontal") {
			fliphorizontal();
		}// 15
		if (action == "Contrast") {
			contrastset.setVisible(true);
			dialogopen = true;
		}// 16
		if (action == "Colour Contrast") {
			colourcontrastset.setVisible(true);
			dialogopen = true;
		}// 17
		if (action == "Colour") {
			colour();
		}// 18
		if (action == "Distribute") {
			regionset.setVisible(true);
			dialogopen = true;
		}// 19
		if (action == "Fun") {
			fun();
		}
		if (action == "Binary") {
			binary();
		}
		if (action == "Pencil Sketch") {
			pencilsketch();
		}
		if (action == "Mean") {
		}
		if (action == "Swap Any Color") {
			entercolor.setVisible(true);
			dialogopen = true;
		}
		if (action == "Minimum") {
			minimumfilter();
		}
		if (action == "Maximum") {
			maximumfilter();
		}
		if (action == "Encrypt") {
			givepassword.setVisible(true);
			password = "encrypt";
			dialogopen = true;
		}
		if (action == "Decrypt") {
			givepassword.setVisible(true);
			password = "decrypt";
			dialogopen = true;
		}
		if (action == "Brightness") {
			brightnesset.setVisible(true);
			dialogopen = true;
		}
		if (action == "Size") {
			adjustsize.setVisible(true);
			dialogopen = true;
		}
		if (action == "Red and Green") {
			swapcolor(1, 1, 0);
		}
		if (action == "Red and Blue") {
			swapcolor(1, 0, 1);
		}
		if (action == "Blue and Green") {
			swapcolor(0, 1, 1);
		}
		if (action == "NOT") {
			booleannot();
		}
		if (action == "Flip arbitrary") {
			giveangle.setVisible(true);
			dialogopen = true;
		}
		if (action == "Crop") {
			crop();
		}
		if (action == "Transpose") {
			transpose();
		}
		if (action == "Emboss") {
			emboss();
		}
		if (action == "Cut") {
			cutimagepart();
		}
		if (action == "Equalize Histogram") {
			histogramequalisation();
		}
		if (action == "Contrast Stretch") {
			stretchcontrast.setVisible(true);
			dialogopen = true;
		}
		if (action == "Gamma") {
			gammabasis.setVisible(true);
			dialogopen = true;
		}
		if (action == "Save as") {
			function();
		}

		if (action.length() > 11 && action.charAt(11) == '.') {
			String turns = action.substring(12, action.length());
			makecolor((int) Double.parseDouble(turns));
		}
		// {String
		// turns=action.substring(12,action.length());makecolor((int)Double.parseDouble(turns));}

		if (action == "Undo") {
			width = widthundo;
			height = heightundo;
			rgbs = new int[width * height];
			for (int a = 0; a < width * height; a++) {
				rgbs[a] = rgbsundoarray[a];

			}

		}

		if (action == "Submit Brightness") {
			brightnessincrement = adjustbrightness.getValue();
			brightnesset.setVisible(false);
			adjustbrightness.setValue(0);
			brightnesset.dispose();
			bright(brightnessincrement);
			dialogopen = false;

		}

		if (action == "Submit Region") {
			regions = adjustregion.getValue();
			regionset.dispose();
			distribute();
			dialogopen = false;
		}

		if (action == "Submit Size") {

			int widt = (int) Double.parseDouble(sizex.getText());
			int hieg = (int) Double.parseDouble(sizey.getText());
			if (maintainaspect.isSelected()) {
				hieg = (int) ((height * widt * 1.0) / (width * 1.0));
			}
			adjustsize.dispose();
			adjustsize(widt, hieg);
			dialogopen = false;
		}
		if (action == "Submit Password") {
			boolean enc = (password == "encrypt") ? true : false;
			password = "";
			char[] input = passwordf.getPassword();
			for (int xa = 0; xa < input.length; xa++) {
				password = password + input[xa];

			}

			// password=passwordf.getText();
			encryptationcycles = (int) Double.parseDouble(numberofcycles
					.getText());
			givepassword.dispose();
			if (enc) {
				for (int a = 0; a < 2 * encryptationcycles; a++) {
					encrypt2();
				}
				encrypt();

			} else {
				decrypt();
				for (int a = 0; a < 2 * encryptationcycles; a++) {
					decrypt2();
				}

			}
			dialogopen = false;
		}

		if (action == "Submit rotation angle") {
			double angle = Double.parseDouble(rotationangle.getText());
			giveangle.dispose();
			fliparbitrary(angle);

			dialogopen = false;

		}

		if (action == "Submit Colour Contrast") {

			int co = adjustcolourcontrast.getValue();
			colourcontrast(co);
			colourcontrastset.setVisible(false);
			adjustcolourcontrast.setValue(0);
			colourcontrastset.dispose();
			dialogopen = false;

		}

		if (action == "Submit Contrast") {

			int co = adjustcontrast.getValue();
			contrast(co);
			contrastset.setVisible(false);
			adjustcontrast.setValue(0);
			contrastset.dispose();
			dialogopen = false;

		}

		if (action == "both colors submitted") {
			Color colorsel1 = tcc1.getColor();
			Color colorsel2 = tcc2.getColor();
			entercolor.dispose();
			double red1 = colorsel1.getRed();
			double green1 = colorsel1.getGreen();
			double blue1 = colorsel1.getBlue();
			double red2 = colorsel2.getRed();
			double green2 = colorsel2.getGreen();
			double blue2 = colorsel2.getBlue();

			anycolorswap(red1, green1, blue1, red2, green2, blue2);
			dialogopen = false;

		}

		if (action == "stretchcontrast submitted") {
			double x = Double.parseDouble(rangecont.getText());
			stretchcontrast.dispose();
			contraststretching(x);
			dialogopen = false;

		}

		if (action == "gamma basis submitted") {
			double x = Double.parseDouble(basisgam.getText());
			gammabasis.dispose();
			gammacorrection(x);
			dialogopen = false;

		}

		if (selrectformed && action != "Crop" && action != "Open"
				&& action != "Save" && action != "Quit") {
			int initialx = (int) (rectix / imagescaleratio);
			int initialy = (int) (rectiy / imagescaleratio);
			int finalx = (int) (rectfx / imagescaleratio);
			int finaly = (int) (rectfy / imagescaleratio);
			int w = finalx - initialx + 1;
			int h = finaly - initialy + 1;
			width = widthbuffer;
			height = heightbuffer;

			BufferedImage buffer1 = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			BufferedImage buffer2 = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
			buffer1.setRGB(0, 0, width, height, rgbsbuffer, 0, width);
			buffer2.setRGB(0, 0, w, h, rgbs, 0, w);
			Graphics2D graregion = buffer1.createGraphics();
			graregion.drawImage(buffer2, initialx, initialy, this);
			rgbs = new int[width * height];
			buffer1.getRGB(0, 0, width, height, rgbs, 0, width);

		}

		selrectformed = false;
		rectix = 0;
		rectiy = 0;
		rectfx = 0;
		rectfy = 0;
		initmousex = 0;
		initmousey = 0;
		finamousex = 0;
		finamousey = 0;
		nowmousex = 0;
		nowmousey = 0;

		BufferedImage buffer = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		buffer.setRGB(0, 0, width, height, rgbs, 0, width);

		// jlabel1.setIcon((jlabel3.getIcon())); // uncomment this line to show
		// just previous image instead of loaded image
		limitingframewidth = 0;
		if (height <= maximageframesize && width <= maximageframesize) {
			limitingframewidth = (height > width) ? height : width;
		} else {
			limitingframewidth = maximageframesize;
		}

		jlabel1.setText("" + width + "x" + height + " Number of pixels : "
				+ width * height);
		bufferimagedisplay = (compressImage(buffer, limitingframewidth * 1.0,
				limitingframewidth * 1.0));
		bufferimagedisplay2 = (compressImage(buffer, limitingframewidth * 1.0,
				limitingframewidth * 1.0));

		jlabel3.setIcon(new ImageIcon(toImage(bufferimagedisplay)));
		jlabel3.setSize(width, height);
		imageframe.setResizable(false);
		imageframe.pack();
		if (!dialogopen && !otherframeopen)
			imageframe.setVisible(true);
		frame.pack();
		frame.setVisible(true);

		// runtimeforgarbagecollection.gc();

	}

	public void mousePressed(MouseEvent me) {
		jlabel3.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		initmousex = me.getX();
		initmousey = me.getY();
		if ((me.getX() < rectfx && me.getX() > rectix)
				&& (me.getY() < rectfy && me.getY() > rectiy)) {
			selectedrectdragged = true;
			dragstartcoordinatex = me.getX();
			dragstartcoordinatey = me.getY();
		} else {
			selectedrectdragged = false;
		}

		if (selrectformed && (!selectedrectdragged)) {

			bufferimagedisplay = copybuffimage(bufferimagedisplay2);

			jlabel3.setIcon(new ImageIcon(toImage(bufferimagedisplay)));
			imageframe.pack();
			imageframe.setVisible(true);
			selrectformed = false;
		}
	}

	public void mouseReleased(MouseEvent me) {
		jlabel3.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		finamousex = me.getX();
		finamousey = me.getY();
		if (initmousex == finamousex && initmousey == finamousey
				&& selrectformed) {
			selectedrectdragged = false;
		}

	}

	public void mouseDragged(MouseEvent me) {

		if (!selectedrectdragged) {
			nowmousex = me.getX();
			nowmousey = me.getY();
			bufferimagedisplay = copybuffimage(bufferimagedisplay2);
			Graphics gracrop = bufferimagedisplay.createGraphics();
			paintcrop(initmousex, initmousey, nowmousex, nowmousey, gracrop);
			selrectformed = true;

			jlabel3.setIcon(new ImageIcon(toImage(bufferimagedisplay)));
		}
		if (selectedrectdragged) {
			nowmousex = me.getX();
			nowmousey = me.getY();

			bufferimagedisplay = copybuffimage(bufferimagedisplay2);
			Graphics gracrop = bufferimagedisplay.createGraphics();
			int x1 = rectix;
			int x2 = rectfx;
			int y1 = rectiy;
			int y2 = rectfy;

			paintcrop(x1 + me.getX() - dragstartcoordinatex, y1 + me.getY()
					- dragstartcoordinatey, x2 + me.getX()
					- dragstartcoordinatex, y2 + me.getY()
					- dragstartcoordinatey, gracrop);
			dragstartcoordinatex = me.getX();
			dragstartcoordinatey = me.getY();
			selrectformed = true;

			jlabel3.setIcon(new ImageIcon(toImage(bufferimagedisplay)));
		}

		selrectformed = true;
	}

	public void mouseClicked(MouseEvent me) {
	}

	public void mouseEntered(MouseEvent me) {
	}

	public void mouseExited(MouseEvent me) {
	}

	public void mouseMoved(MouseEvent me) {

		int mouseex = (int) (me.getX() / imagescaleratio);
		int mouseey = (int) (me.getY() / imagescaleratio);
		Color col = new Color(bufferimagedisplay.getRGB(me.getX(), me.getY()));

		// jlabel1.setText(""+width+"x"+height+" pixels : "+width*height+" X :"+me.getX()+" Y :"+me.getY()+" "+col.getRed()+","+col.getGreen()+","+col.getBlue());
		jlabel1.setText("" + width + "x" + height + " pixels : " + width
				* height + " X :" + mouseex + " Y :" + mouseey + " "
				+ col.getRed() + "," + col.getGreen() + "," + col.getBlue());
	}

	public void itemStateChanged(ItemEvent ie) {
	}

	public void stateChanged(ChangeEvent e) {
	}

	public static Image toImage(BufferedImage bufferedImage) {
		return Toolkit.getDefaultToolkit().createImage(
				bufferedImage.getSource());
	}

	public BufferedImage compressImage(BufferedImage bufferimage, double wid,
			double hiet) {

		AffineTransform tx = new AffineTransform();
		double ratio = (width > height) ? (wid / width) : (hiet / height);
		tx.scale(ratio, ratio);
		tx.shear(0, 0);
		tx.translate(0, 0);
		tx.rotate(0);
		imagescaleratio = ratio;
		// System.out.println(""+tx.getDeterminant());
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		bufferimage = op.filter(bufferimage, null);
		return bufferimage;
	}

	public Image loadimage(File file) {
		Image image = null;

		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
		}
		return image;
	}

	public void saveimage(BufferedImage imagesave, File file) {

		try {
			ImageIO.write(imagesave, "png", file);
		} catch (Exception e) {
		}
	}

	public void invert() {
		int a = 0;
		for (a = 0; a < rgbs.length; a++) {
			Color col = new Color(rgbs[a]);
			int red = 255 - col.getRed();
			int blue = 255 - col.getBlue();
			int green = 255 - col.getGreen();
			int alpha = 255 - col.getAlpha();

			col = new Color(red, green, blue, alpha);
			rgbs[a] = col.getRGB();
		}
	}

	public void blackandwhite() {
		int a = 0;
		for (a = 0; a < rgbs.length; a++) {
			Color col = new Color(rgbs[a]);
			int component = (int) (.299 * col.getRed() + .114 * col.getBlue() + .587 * col
					.getGreen());

			col = new Color(component, component, component);
			rgbs[a] = col.getRGB();

		}
	}

	public void smoothing() {

		int a = 0;
		int w = width;
		int h = height;
		int rgbsx[] = new int[w * h];
		for (a = 0; a < rgbs.length; a++) {
			if (!((a < w) || (a > (w * (h - 1) - 1)) || (a % w == 0) || ((a + 1) % (w)) == 0)) {

				Color col1 = new Color(rgbs[a - w - 1]);
				Color col2 = new Color(rgbs[a - w]);
				Color col3 = new Color(rgbs[a - w + 1]);
				Color col4 = new Color(rgbs[a - 1]);
				Color col5 = new Color(rgbs[a]);
				Color col6 = new Color(rgbs[a + 1]);
				Color col7 = new Color(rgbs[a + w - 1]);
				Color col8 = new Color(rgbs[a + w]);
				Color col9 = new Color(rgbs[a + w + 1]);

				int red1 = col1.getRed();
				int red2 = col2.getRed();
				int red3 = col3.getRed();
				int red4 = col4.getRed();
				int red5 = col5.getRed();
				int red6 = col6.getRed();
				int red7 = col7.getRed();
				int red8 = col8.getRed();
				int red9 = col9.getRed();

				int blue1 = col1.getBlue();
				int blue2 = col2.getBlue();
				int blue3 = col3.getBlue();
				int blue4 = col4.getBlue();
				int blue5 = col5.getBlue();
				int blue6 = col6.getBlue();
				int blue7 = col7.getBlue();
				int blue8 = col8.getBlue();
				int blue9 = col9.getBlue();

				int green1 = col1.getGreen();
				int green2 = col2.getGreen();
				int green3 = col3.getGreen();
				int green4 = col4.getGreen();
				int green5 = col5.getGreen();
				int green6 = col6.getGreen();
				int green7 = col7.getGreen();
				int green8 = col8.getGreen();
				int green9 = col9.getGreen();

				red5 = (red5 + red1 + red2 + red3 + red4 + red6 + red7 + red8 + red9) / 9;
				blue5 = (blue5 + blue1 + blue2 + blue3 + blue4 + blue6 + blue7
						+ blue8 + blue9) / 9;
				green5 = (green5 + green1 + green2 + green3 + green4 + green6
						+ green7 + green8 + green9) / 9;

				Color col = new Color(red5, green5, blue5);
				rgbsx[a] = col.getRGB();

			}
		}
		for (int qw = 0; qw < width * height; qw++) {
			rgbs[qw] = rgbsx[qw];
		}

	}

	public void weightedsmoothing() {

		int a = 0;
		int w = width;
		int h = height;
		for (a = 0; a < rgbs.length; a++) {
			if (!((a < w) || (a > (w * (h - 1) - 1)) || (a % w == 0) || ((a + 1) % (w)) == 0)) {

				Color col1 = new Color(rgbs[a - w - 1]);
				Color col2 = new Color(rgbs[a - w]);
				Color col3 = new Color(rgbs[a - w + 1]);
				Color col4 = new Color(rgbs[a - 1]);
				Color col5 = new Color(rgbs[a]);
				Color col6 = new Color(rgbs[a + 1]);
				Color col7 = new Color(rgbs[a + w - 1]);
				Color col8 = new Color(rgbs[a + w]);
				Color col9 = new Color(rgbs[a + w + 1]);

				int red1 = col1.getRed();
				int red2 = col2.getRed();
				int red3 = col3.getRed();
				int red4 = col4.getRed();
				int red5 = col5.getRed();
				int red6 = col6.getRed();
				int red7 = col7.getRed();
				int red8 = col8.getRed();
				int red9 = col9.getRed();

				int blue1 = col1.getBlue();
				int blue2 = col2.getBlue();
				int blue3 = col3.getBlue();
				int blue4 = col4.getBlue();
				int blue5 = col5.getBlue();
				int blue6 = col6.getBlue();
				int blue7 = col7.getBlue();
				int blue8 = col8.getBlue();
				int blue9 = col9.getBlue();

				int green1 = col1.getGreen();
				int green2 = col2.getGreen();
				int green3 = col3.getGreen();
				int green4 = col4.getGreen();
				int green5 = col5.getGreen();
				int green6 = col6.getGreen();
				int green7 = col7.getGreen();
				int green8 = col8.getGreen();
				int green9 = col9.getGreen();

				red5 = (4 * red5 + red1 + 2 * red2 + red3 + 2 * red4 + 2 * red6
						+ red7 + 2 * red8 + red9) / 16;
				blue5 = (4 * blue5 + blue1 + 2 * blue2 + blue3 + 2 * blue4 + 2
						* blue6 + blue7 + 2 * blue8 + blue9) / 16;
				green5 = (4 * green5 + green1 + 2 * green2 + green3 + 2
						* green4 + 2 * green6 + green7 + 2 * green8 + green9) / 16;

				Color col = new Color(red5, green5, blue5);
				rgbs[a] = col.getRGB();
			}
		}
	}

	public void filtermedian() {

		int a = 0;
		int w = width;
		int h = height;
		Color col[] = new Color[9];
		int intense[] = new int[9];
		int intensecopy[] = new int[9];
		int rgbsx[] = new int[w * h];
		for (a = 0; a < rgbs.length; a++) {
			if (!((a < w) || (a > (w * (h - 1) - 1)) || (a % w == 0) || ((a + 1) % (w)) == 0)) {

				col[0] = new Color(rgbs[a - w - 1]);
				col[1] = new Color(rgbs[a - w]);
				col[2] = new Color(rgbs[a - w + 1]);
				col[3] = new Color(rgbs[a - 1]);
				col[4] = new Color(rgbs[a]);
				col[5] = new Color(rgbs[a + 1]);
				col[6] = new Color(rgbs[a + w - 1]);
				col[7] = new Color(rgbs[a + w]);
				col[8] = new Color(rgbs[a + w + 1]);

				for (int b = 0; b < 9; b++) {
					intense[b] = (col[b].getRed() + col[b].getGreen() + col[b]
							.getBlue()) / 3;
					intensecopy[b] = intense[b];
				}
				Arrays.sort(intense);
				int element = intense[4];
				int index = 4;
				for (int c = 0; c < 9; c++) {
					if (element == intensecopy[c]) {
						index = c;
					}
				}
				rgbsx[a] = col[index].getRGB();
			}
		}
		for (a = 0; a < width * height; a++) {
			rgbs[a] = rgbsx[a];
		}
	}

	public void filtermedian2() {

		int a = 0;
		int w = width;
		int h = height;
		Color col[] = new Color[9];
		int intense[] = new int[9];
		int intensecopy[] = new int[9];
		for (a = 0; a < rgbs.length; a++) {
			if (!((a < w) || (a > (w * (h - 1) - 1)) || (a % w == 0) || ((a + 1) % (w)) == 0)) {

				col[0] = new Color(rgbs[a - w - 1]);
				col[1] = new Color(rgbs[a - w]);
				col[2] = new Color(rgbs[a - w + 1]);
				col[3] = new Color(rgbs[a - 1]);
				col[4] = new Color(rgbs[a]);
				col[5] = new Color(rgbs[a + 1]);
				col[6] = new Color(rgbs[a + w - 1]);
				col[7] = new Color(rgbs[a + w]);
				col[8] = new Color(rgbs[a + w + 1]);

				for (int b = 0; b < 9; b++) {
					intense[b] = (col[b].getRed() + col[b].getGreen() + col[b]
							.getBlue()) / 3;
					intensecopy[b] = intense[b];
				}
				Arrays.sort(intense);
				int element = intense[4];
				int index = 4;
				for (int c = 0; c < 9; c++) {
					if (element == intensecopy[c]) {
						index = c;
					}
				}
				rgbs[a] = col[index].getRGB();
			}
		}

	}

	public void threshholdaveraging() {
		int a = 0;
		int w = width;
		int h = height;
		for (a = 0; a < rgbs.length; a++) {
			if (!((a < w) || (a > (w * (h - 1) - 1)) || (a % w == 0) || ((a + 1) % (w)) == 0)) {

				Color col1 = new Color(rgbs[a - w - 1]);
				Color col2 = new Color(rgbs[a - w]);
				Color col3 = new Color(rgbs[a - w + 1]);
				Color col4 = new Color(rgbs[a - 1]);
				Color col5 = new Color(rgbs[a]);
				Color col6 = new Color(rgbs[a + 1]);
				Color col7 = new Color(rgbs[a + w - 1]);
				Color col8 = new Color(rgbs[a + w]);
				Color col9 = new Color(rgbs[a + w + 1]);

				int red1 = col1.getRed();
				int red2 = col2.getRed();
				int red3 = col3.getRed();
				int red4 = col4.getRed();
				int red5 = col5.getRed();
				int red6 = col6.getRed();
				int red7 = col7.getRed();
				int red8 = col8.getRed();
				int red9 = col9.getRed();

				int blue1 = col1.getBlue();
				int blue2 = col2.getBlue();
				int blue3 = col3.getBlue();
				int blue4 = col4.getBlue();
				int blue5 = col5.getBlue();
				int blue6 = col6.getBlue();
				int blue7 = col7.getBlue();
				int blue8 = col8.getBlue();
				int blue9 = col9.getBlue();

				int green1 = col1.getGreen();
				int green2 = col2.getGreen();
				int green3 = col3.getGreen();
				int green4 = col4.getGreen();
				int green5 = col5.getGreen();
				int green6 = col6.getGreen();
				int green7 = col7.getGreen();
				int green8 = col8.getGreen();
				int green9 = col9.getGreen();

				int redx = (red5 + red1 + red2 + red3 + red4 + red6 + red7
						+ red8 + red9) / 9;
				int bluex = (blue5 + blue1 + blue2 + blue3 + blue4 + blue6
						+ blue7 + blue8 + blue9) / 9;
				int greenx = (green5 + green1 + green2 + green3 + green4
						+ green6 + green7 + green8 + green9) / 9;

				if ((redx - red5) * (redx - red5) > 5) {
					red5 = redx;
				}
				if ((bluex - blue5) * (bluex - blue5) > 5) {
					blue5 = bluex;
				}
				if ((greenx - green5) * (greenx - green5) > 5) {
					green5 = greenx;
				}
				Color col = new Color(red5, green5, blue5);
				rgbs[a] = col.getRGB();
			}
		}
	}

	public void conservative2() {

		int a = 0;
		int w = width;
		int h = height;
		Color col[] = new Color[25];
		int intense[] = new int[25];
		for (a = 0; a < rgbs.length; a++) {
			if (!((a < 2 * w) || (a > (w * (h - 2) - 1)) || (a % w == 0)
					|| ((a + 1) % (w)) == 0 || ((a - 1) % w == 0) || ((a + 2)
					% w == 0))) {

				for (int x = 0; x < 25; x++) {
					// System.out.println(""+a+((x/5)-2)*w+(x-2));
					// System.out.println(""+w+h);
					col[x] = new Color(
							rgbs[a + ((x / 5) - 2) * w + (x % 5 - 2)]);

				}

				int max = -1;
				int min = 10000;

				for (int b = 0; b < 25; b++) {
					intense[b] = col[b].getRed() + col[b].getGreen()
							+ col[b].getBlue();
					if (b != 12) {
						max = (intense[b] > max) ? intense[b] : max;
						min = (intense[b] < min) ? intense[b] : min;
					}
				}

				int index;
				if (max < intense[12]) {
					index = 24;
				} else if (min > intense[12]) {
					index = 0;
				} else {
					index = 12;
				}
				rgbs[a] = col[index].getRGB();
			}
		}
	}

	public void conservative() {

		int a = 0;
		int w = width;
		int h = height;
		Color col[] = new Color[9];
		int intense[] = new int[9];
		for (a = 0; a < rgbs.length; a++) {
			if (!((a < w) || (a > (w * (h - 1) - 1)) || (a % w == 0) || ((a + 1) % (w)) == 0)) {

				for (int x = 0; x < 9; x++) {
					// System.out.println(""+a+((x/5)-2)*w+(x-2));
					// System.out.println(""+w+h);
					col[x] = new Color(
							rgbs[a + ((x / 3) - 1) * w + (x % 3 - 1)]);

				}

				int max = -1;
				int min = 10000;

				for (int b = 0; b < 9; b++) {
					intense[b] = col[b].getRed() + col[b].getGreen()
							+ col[b].getBlue();
					if (b != 4) {
						max = (intense[b] > max) ? intense[b] : max;
						min = (intense[b] < min) ? intense[b] : min;
					}
				}

				int index;
				if (max < intense[4]) {
					index = 8;
				} else if (min > intense[4]) {
					index = 0;
				} else {
					index = 4;
				}
				rgbs[a] = col[index].getRGB();
			}
		}
	}

	public void findedges() {

		int w = width;
		int h = height;

		int rgbs2[] = new int[w * h];

		for (int b = 0; b < 1; b++) {
			for (int a = 0; a < rgbs.length; a++) {
				if (!((a < w) || (a > (w * (h - 1) - 1)) || (a % w == 0) || ((a + 1) % (w)) == 0)) {

					Color col2 = new Color(rgbs[a - w]);
					Color col4 = new Color(rgbs[a - 1]);
					Color col5 = new Color(rgbs[a]);
					Color col6 = new Color(rgbs[a + 1]);
					Color col8 = new Color(rgbs[a + w]);

					int red2 = col2.getRed();
					int red4 = col4.getRed();
					int red5 = col5.getRed();
					int red6 = col6.getRed();
					int red8 = col8.getRed();

					int blue2 = col2.getBlue();
					int blue4 = col4.getBlue();
					int blue5 = col5.getBlue();
					int blue6 = col6.getBlue();
					int blue8 = col8.getBlue();

					int green2 = col2.getGreen();
					int green4 = col4.getGreen();
					int green5 = col5.getGreen();
					int green6 = col6.getGreen();
					int green8 = col8.getGreen();

					red5 = (red5 + red2 + red4 + red6 + red8) / 5;
					blue5 = (blue5 + blue2 + blue4 + blue6 + blue8) / 5;
					green5 = (green5 + green2 + green4 + green6 + green8) / 5;

					Color col = new Color(red5, green5, blue5);
					rgbs2[a] = col.getRGB();

				}
			}

		}

		// System.out.println("part 1 completed");

		for (int a = 0; a < w * h; a++) {

			int red = (new Color(rgbs[a])).getRed();
			int blue = (new Color(rgbs[a])).getBlue();
			int green = (new Color(rgbs[a])).getGreen();

			int red1 = (new Color(rgbs2[a])).getRed();
			int blue1 = (new Color(rgbs2[a])).getBlue();
			int green1 = (new Color(rgbs2[a])).getGreen();

			red1 = Math.abs(red1 - red);
			green1 = Math.abs(green1 - green);
			blue1 = Math.abs(blue1 - blue);

			red1 = (red1 * 10 > 255) ? 255 : red1 * 10;
			green1 = (green1 * 10 > 255) ? 255 : green1 * 10;
			blue1 = (blue1 * 10 > 255) ? 255 : blue1 * 10;

			Color col = new Color((int) (red1), (int) (green1), (int) (blue1));
			rgbs[a] = col.getRGB();

		}

	}

	public void drawhist2(Graphics grahist) {

		int w = width;
		int h = height;

		int intense[] = new int[256];
		int reda[] = new int[256];
		int greena[] = new int[256];
		int bluea[] = new int[256];

		int max = 0;
		int maxred = 0;
		int maxblue = 0;
		int maxgreen = 0;
		int maxintensity = 0;
		int minintensity = 10000;
		double meanintensity = 0;
		for (int a = 0; a < w * h; a++) {

			int red = (new Color(rgbs[a])).getRed();
			int blue = (new Color(rgbs[a])).getBlue();
			int green = (new Color(rgbs[a])).getGreen();
			int intensity = Math.abs((red + blue + green) / 3);
			maxintensity = (intensity > maxintensity) ? intensity
					: maxintensity;
			minintensity = (intensity < minintensity) ? intensity
					: minintensity;
			intense[intensity]++;
			reda[red]++;
			greena[green]++;
			bluea[blue]++;

		}
		for (int b = 0; b < 256; b++) {
			max = (intense[b] > max) ? intense[b] : max;
			maxred = (reda[b] > maxred) ? reda[b] : maxred;
			maxgreen = (greena[b] > maxgreen) ? greena[b] : maxgreen;
			maxblue = (bluea[b] > maxblue) ? bluea[b] : maxblue;
			meanintensity = meanintensity + intense[b] * b;
		}
		meanintensity = meanintensity / (w * h);

		max = (max > maxred) ? max : maxred;
		max = (max > maxgreen) ? max : maxgreen;
		max = (max > maxblue) ? max : maxblue;

		double scale = 256.0 / max;
		int xpace = 10;
		for (int a = 0; a < 256; a++) {
			grahist.setColor(Color.white);
			// grahist.drawOval(xpace+a*2,300-(int)(intense[a]*scale),1,1);

			if (a != 0) {
				grahist.drawLine(xpace + a * 2,
						300 - (int) (intense[a] * scale), xpace + (a - 1) * 2,
						300 - (int) (intense[a - 1] * scale));
				grahist.setColor(Color.red);
				grahist.drawLine(xpace + a * 2, 300 - (int) (reda[a] * scale),
						xpace + (a - 1) * 2, 300 - (int) (reda[a - 1] * scale));
				grahist.setColor(Color.green);
				grahist.drawLine(xpace + a * 2,
						300 - (int) (greena[a] * scale), xpace + (a - 1) * 2,
						300 - (int) (greena[a - 1] * scale));
				grahist.setColor(Color.blue);
				grahist.drawLine(xpace + a * 2, 300 - (int) (bluea[a] * scale),
						xpace + (a - 1) * 2, 300 - (int) (bluea[a - 1] * scale));
			}
			grahist.setColor(Color.red);
			grahist.drawLine(10, 300, 512, 300);

			// uncomment following lines to give a blue look to hist
			// grahist.setColor(Color.blue);
			// grahist.drawLine(xpace+a*2,300,xpace+a*2,302-(int)(intense[a]*scale));
		}

		grahist.setColor(new Color(100, 100, 100));
		grahist.drawRect(8, 40, 514, 260);

		grahist.setColor(new Color(200, 200, 200));
		grahist.setFont(new Font("Dialog", Font.BOLD, 12));
		grahist.drawString("Pixel Count : " + w * h, 100, 320);
		grahist.drawString("Minimum : " + minintensity, 100, 335);
		grahist.drawString("Maximum : " + maxintensity, 100, 350);
		grahist.drawString("Mean : " + meanintensity, 100, 365);

	}

	public void drawhist(Graphics grahist) {

		int w = width;
		int h = height;

		int intense[] = new int[256];
		int max = 0;
		int maxintensity = 0;
		int minintensity = 10000;
		double meanintensity = 0;
		for (int a = 0; a < w * h; a++) {

			int red = (new Color(rgbs[a])).getRed();
			int blue = (new Color(rgbs[a])).getBlue();
			int green = (new Color(rgbs[a])).getGreen();
			int intensity = Math.abs((red + blue + green) / 3);
			maxintensity = (intensity > maxintensity) ? intensity
					: maxintensity;
			minintensity = (intensity < minintensity) ? intensity
					: minintensity;
			intense[intensity]++;

		}
		for (int b = 0; b < 256; b++) {
			max = (intense[b] > max) ? intense[b] : max;
			meanintensity = meanintensity + intense[b] * b;
		}
		meanintensity = meanintensity / (w * h);

		double scale = 256.0 / max;
		int xpace = 10;
		for (int a = 0; a < 256; a++) {
			grahist.setColor(Color.green);
			grahist.drawOval(xpace + a * 2, 300 - (int) (intense[a] * scale),
					1, 1);

			if (a != 0) {
				grahist.drawLine(xpace + a * 2,
						300 - (int) (intense[a] * scale), xpace + (a - 1) * 2,
						300 - (int) (intense[a - 1] * scale));
			}
			grahist.setColor(Color.red);
			grahist.drawLine(10, 300, 512, 300);

			// uncomment following lines to give a blue look to hist
			// grahist.setColor(Color.blue);
			// grahist.drawLine(xpace+a*2,300,xpace+a*2,302-(int)(intense[a]*scale));
		}

		grahist.setColor(Color.white);
		grahist.drawRect(8, 40, 514, 260);

		grahist.setFont(new Font("Dialog", Font.BOLD, 12));
		grahist.drawString("Pixel Count : " + w * h, 100, 320);
		grahist.drawString("Minimum : " + minintensity, 100, 335);
		grahist.drawString("Maximum : " + maxintensity, 100, 350);
		grahist.drawString("Mean : " + meanintensity, 100, 365);

	}

	/*
	 * public void squareroot(){
	 * 
	 * for(int a=0;a<width*height;a++){ int red=(new Color(rgbs[a])).getRed();
	 * int blue=(new Color(rgbs[a])).getBlue(); int green=(new
	 * Color(rgbs[a])).getGreen();
	 * 
	 * Color col = new
	 * Color((int)Math.pow(red,0.5),(int)Math.pow(green,0.5),(int
	 * )Math.pow(blue,0.5)); rgbs[a]=col.getRGB();
	 * 
	 * }
	 * 
	 * }
	 */

	public void log() {

		for (int a = 0; a < width * height; a++) {
			int red = (new Color(rgbs[a])).getRed();
			int blue = (new Color(rgbs[a])).getBlue();
			int green = (new Color(rgbs[a])).getGreen();

			if (red != 0)
				red = (int) Math.log(red);
			if (green != 0)
				green = (int) Math.log(green);
			if (blue != 0)
				blue = (int) Math.log(blue);

			Color col = new Color(red, green, blue);
			rgbs[a] = col.getRGB();

		}

	}

	public void square() {

		for (int a = 0; a < width * height; a++) {
			int red = (new Color(rgbs[a])).getRed();
			int blue = (new Color(rgbs[a])).getBlue();
			int green = (new Color(rgbs[a])).getGreen();

			red = (red * red > 255) ? 255 : red * red;
			green = (green * green > 255) ? 255 : green * green;
			blue = (blue * blue > 255) ? 255 : blue * blue;

			Color col = new Color(red, green, blue);
			rgbs[a] = col.getRGB();

		}

	}

	public void reciprocal() {

		for (int a = 0; a < width * height; a++) {
			int red = (new Color(rgbs[a])).getRed();
			int blue = (new Color(rgbs[a])).getBlue();
			int green = (new Color(rgbs[a])).getGreen();

			double byred = (255.0) / (red * 1.0 + 1.0);
			double bygreen = (255.0) / (green * 1.0 + 1.0);
			double byblue = (255.0) / (blue * 1.0 + 1.0);

			Color col = new Color((int) byred, (int) bygreen, (int) byblue);
			rgbs[a] = col.getRGB();

		}

	}

	public void flipvertical() {

		int rgbsx[] = new int[width * height];
		int index = 0;
		for (int a = 0; a < height; a++) {
			for (int b = 0; b < width; b++) {
				rgbsx[index] = rgbs[(height - a - 1) * width + b];
				index++;
			}
		}
		for (int a = 0; a < width * height; a++) {
			rgbs[a] = rgbsx[a];
		}
	}

	public void fliphorizontal() {

		int rgbsx[] = new int[width * height];
		int index = 0;
		for (int a = 0; a < height; a++) {
			for (int b = 0; b < width; b++) {
				rgbsx[index] = rgbs[(a + 1) * width - 1 - b];
				index++;
			}
		}
		for (int a = 0; a < width * height; a++) {
			rgbs[a] = rgbsx[a];
		}
	}

	public void fliparbitrary(double anglerot) {

		BufferedImage bufferimagethis = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		bufferimagethis.setRGB(0, 0, width, height, rgbs, 0, width);

		AffineTransform tx = new AffineTransform();

		tx.scale(1.0, 1.0);
		tx.shear(0, 0);
		tx.translate(0, 0);
		tx.rotate(anglerot, bufferimagethis.getWidth() / 2,
				bufferimagethis.getHeight() / 2);
		// System.out.println(""+tx.getDeterminant());

		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		bufferimagethis = op.filter(bufferimagethis, null);

		rgbs = new int[width * height];
		bufferimagethis.getRGB(0, 0, width, height, rgbs, 0, width);

	}

	public void findedges2() {

		int rgbsx[] = new int[width * height];
		int colour[][] = new int[width * height][4];
		int w = width;
		int h = height;

		for (int a = 0; a < width * height; a++) {

			Color col = new Color(rgbs[a]);
			colour[a][0] = col.getRed();
			colour[a][1] = col.getGreen();
			colour[a][2] = col.getBlue();
			colour[a][3] = col.getRed() + col.getGreen() + col.getBlue();
		}

		for (int a = 0; a < width * height; a++) {
			int checker = 0;
			if (!((a < w) || (a > (w * (h - 1) - 1)) || (a % w == 0) || ((a + 1) % (w)) == 0)) {
				if (colour[a][3] == colour[a - w - 1][3]) {
					checker++;
				}
				if (colour[a][3] == colour[a - w][3]) {
					checker++;
				}
				if (colour[a][3] == colour[a - w + 1][3]) {
					checker++;
				}
				if (colour[a][3] == colour[a - 1][3]) {
					checker++;
				}
				if (colour[a][3] == colour[a + 1][3]) {
					checker++;
				}
				if (colour[a][3] == colour[a + w - 1][3]) {
					checker++;
				}
				if (colour[a][3] == colour[a + w][3]) {
					checker++;
				}
				if (colour[a][3] == colour[a + w + 1][3]) {
					checker++;
				}
				if (checker != 5) {
					rgbs[a] = (Color.black).getRGB();
				}
				if (checker == 5) {
					rgbs[a] = (Color.white).getRGB();
				}
			}
		}
	}

	public void contrast1() {

		for (int a = 0; a < width * height; a++) {
			Color col = new Color(rgbs[a]);
			int red = col.getRed();
			int green = col.getGreen();
			int blue = col.getBlue();

			if (red > 128) {
				red = (red * 1.2 > 255) ? 255 : (int) (red * 1.2);
			}
			if (red < 128) {
				red = (int) (red / 1.2);
			}

			if (green > 128) {
				green = (green * 1.2 > 255) ? 255 : (int) (green * 1.2);
			}
			if (green < 128) {
				green = (int) (green / 1.2);
			}

			if (blue > 128) {
				blue = (blue * 1.2 > 255) ? 255 : (int) (blue * 1.2);
			}
			if (blue < 128) {
				blue = (int) (blue / 1.2);
			}

			rgbs[a] = (new Color(red, green, blue)).getRGB();
		}
	}

	public void contrast(int percentage) {

		double mean = 0;
		double adder = (1.0 + (percentage / 100.0));

		for (int a = 0; a < width * height; a++) {
			Color col = new Color(rgbs[a]);
			mean = mean + col.getRed() + col.getGreen() + col.getBlue();
		}
		mean = mean / (width * height);
		for (int a = 0; a < width * height; a++) {
			Color col = new Color(rgbs[a]);
			double red = col.getRed();
			double green = col.getGreen();
			double blue = col.getBlue();
			if (percentage > 0) {
				if (red + green + blue > mean) {
					red = (red * adder < 255) ? red * adder : 255;
					green = (green * adder < 255) ? green * adder : 255;
					blue = (blue * adder < 255) ? blue * adder : 255;
				}
				if (red + green + blue < mean) {
					red = red / adder;
					green = green / adder;
					blue = blue / adder;
				}
			}
			if (percentage < 0) {
				if (red + green + blue > mean) {
					red = red * adder;
					green = green * adder;
					blue = blue * adder;
				}
				if (red + green + blue < mean) {
					red = (red / adder > 255) ? 255 : red / adder;
					green = (green / adder > 255) ? 255 : green / adder;
					blue = (blue / adder > 255) ? 255 : blue / adder;
				}
			}

			rgbs[a] = (new Color((int) red, (int) green, (int) blue)).getRGB();
		}

	}

	public void sharpen2() {
		int rgbsx[] = new int[width * height];
		int w = width;
		int h = height;

		for (int a = 0; a < width * height; a++) {
			if (!((a < w) || (a > (w * (h - 1) - 1)) || (a % w == 0) || ((a + 1) % (w)) == 0)) {

				Color col1 = new Color(rgbs[a - w - 1]);
				Color col2 = new Color(rgbs[a - w]);
				Color col3 = new Color(rgbs[a - w + 1]);
				Color col4 = new Color(rgbs[a - 1]);
				Color col5 = new Color(rgbs[a]);
				Color col6 = new Color(rgbs[a + 1]);
				Color col7 = new Color(rgbs[a + w - 1]);
				Color col8 = new Color(rgbs[a + w]);
				Color col9 = new Color(rgbs[a + w + 1]);

				int redaver = (col1.getRed() + col2.getRed() + col3.getRed()
						+ col4.getRed() + col6.getRed() + col7.getRed()
						+ col8.getRed() + col9.getRed()) / 8;
				int greenaver = (col1.getGreen() + col2.getGreen()
						+ col3.getGreen() + col4.getGreen() + col6.getGreen()
						+ col7.getGreen() + col8.getGreen() + col9.getGreen()) / 8;
				int blueaver = (col1.getBlue() + col2.getBlue()
						+ col3.getBlue() + col4.getBlue() + col6.getBlue()
						+ col7.getBlue() + col8.getBlue() + col9.getBlue()) / 8;

				int red = col5.getRed() - redaver + col5.getRed();
				if (red > 255) {
					red = 255;
				}
				if (red < 0) {
					red = 0;
				}

				int blue = col5.getBlue() - blueaver + col5.getBlue();
				if (blue > 255) {
					blue = 255;
				}
				if (blue < 0) {
					blue = 0;
				}

				int green = col5.getGreen() - greenaver + col5.getGreen();
				if (green > 255) {
					green = 255;
				}
				if (green < 0) {
					green = 0;
				}

				rgbsx[a] = (new Color(red, green, blue)).getRGB();
			}
		}
		for (int a = 0; a < width * height; a++) {
			rgbs[a] = rgbsx[a];
		}
	}

	public void distribute() {

		int colour = (255) / regions;
		int maxin = 0;
		int minin = 1000;

		for (int a = 0; a < width * height; a++) {
			int intense = ((new Color(rgbs[a])).getRed()
					+ (new Color(rgbs[a])).getGreen() + (new Color(rgbs[a]))
					.getBlue()) / 3;
			maxin = (maxin > intense) ? maxin : intense;
			minin = (minin < intense) ? minin : intense;
			Color col = new Color(intense, intense, intense);
			rgbs[a] = col.getRGB();
		}

		int diff = (maxin - minin) / regions;
		for (int a = 0; a < width * height; a++) {
			int intense = ((new Color(rgbs[a])).getRed()
					+ (new Color(rgbs[a])).getGreen() + (new Color(rgbs[a]))
					.getBlue()) / 3;
			int ireg = (intense - minin) / diff;

			Color col = new Color(ireg * colour, ireg * colour, ireg * colour);
			// System.out.println(""+maxin+" "+minin+" "+colour+" "+diff+" "+ireg);
			rgbs[a] = col.getRGB();

		}

	}

	public void colour() {

		int colour = (255) / regions;
		int maxin = 0;
		int minin = 1000;
		int colarr[][] = new int[regions + 100][3];

		for (int a = 0; a < regions + 5; a++) {
			colarr[a][0] = (int) (Math.random() * 255);
			colarr[a][1] = (int) (Math.random() * 255);
			colarr[a][2] = (int) (Math.random() * 255);
		}

		for (int a = 0; a < width * height; a++) {
			int intense = ((new Color(rgbs[a])).getRed()
					+ (new Color(rgbs[a])).getGreen() + (new Color(rgbs[a]))
					.getBlue()) / 3;
			maxin = (maxin > intense) ? maxin : intense;
			minin = (minin < intense) ? minin : intense;
			Color col = new Color(intense, intense, intense);
			rgbs[a] = col.getRGB();
		}

		int diff = (maxin - minin) / regions;
		for (int a = 0; a < width * height; a++) {
			int intense = ((new Color(rgbs[a])).getRed()
					+ (new Color(rgbs[a])).getGreen() + (new Color(rgbs[a]))
					.getBlue()) / 3;
			int ireg = (intense - minin) / diff;

			Color col = new Color(colarr[ireg][0], colarr[ireg][1],
					colarr[ireg][2]);
			// System.out.println(""+maxin+" "+minin+" "+colour+" "+diff+" "+ireg);
			rgbs[a] = col.getRGB();

		}

	}

	public void fun() {
		for (int a = 0; a < width * height; a++) {
			Color col = new Color(rgbs[a]);
			int red = col.getRed();
			int green = col.getGreen();
			int blue = col.getBlue();

			red = (int) (red / 1.2);
			green = (int) (green / 1.2);
			blue = (int) (blue / 1.2);
			col = new Color(red, green, blue);
			rgbs[a] = col.getRGB();
		}

	}

	public void showinfo(Graphics grahist) {
		grahist.setColor(Color.black);
		grahist.fillRect(0, 0, 532, 532);
		grahist.setColor(new Color(220, 220, 220));
		grahist.setFont(new Font("Dialog", Font.BOLD, 12));
		grahist.drawLine(0, 35, 500, 35);
		grahist.drawString("File name : " + filename, 4, 50);
		grahist.drawLine(0, 55, 500, 55);
		grahist.drawString("Width : " + width, 4, 70);
		grahist.drawLine(0, 75, 500, 75);
		grahist.drawString("Height : " + height, 4, 90);
		grahist.drawLine(0, 95, 500, 95);
		grahist.drawString("Number of pixels : " + width * height, 4, 110);
		grahist.drawLine(0, 115, 500, 115);

	}

	public void binary() {
		double intensityaver = 0;
		for (int a = 0; a < width * height; a++) {
			intensityaver = intensityaver
					+ ((new Color(rgbs[a])).getRed()
							+ (new Color(rgbs[a])).getGreen() + (new Color(
							rgbs[a])).getBlue()) / 3;
		}
		intensityaver = intensityaver / (width * height);
		for (int a = 0; a < width * height; a++) {
			if (((new Color(rgbs[a])).getRed()
					+ (new Color(rgbs[a])).getGreen() + (new Color(rgbs[a]))
					.getBlue()) / 3 > intensityaver) {
				rgbs[a] = (Color.white).getRGB();
			} else {
				rgbs[a] = (Color.black).getRGB();
			}

		}

	}

	public void colourcontrast(int percentage) {

		int redmin = 0;
		int greenmin = 0;
		int bluemin = 0;
		double adder = 1.0 + (percentage / 100.0);

		for (int a = 0; a < width * height; a++) {
			Color col = new Color(rgbs[a]);
			int red = col.getRed();
			int green = col.getGreen();
			int blue = col.getBlue();

			redmin = redmin + red;
			greenmin = greenmin + green;
			bluemin = bluemin + blue;

		}
		redmin = redmin / (width * height);
		greenmin = greenmin / (width * height);
		bluemin = bluemin / (width * height);

		for (int a = 0; a < width * height; a++) {
			Color col = new Color(rgbs[a]);
			int red = col.getRed();
			int green = col.getGreen();
			int blue = col.getBlue();
			if (percentage > 0) {
				if (red > redmin) {
					red = (red * adder > 255) ? 255 : (int) (red * adder);
				}
				if (red < redmin) {
					red = (int) (red / adder);
				}

				if (green > greenmin) {
					green = (green * adder > 255) ? 255 : (int) (green * adder);
				}
				if (green < greenmin) {
					green = (int) (green / adder);
				}

				if (blue > bluemin) {
					blue = (blue * adder > 255) ? 255 : (int) (blue * adder);
				}
				if (blue < bluemin) {
					blue = (int) (blue / adder);
				}
			}

			if (percentage < 0) {
				if (red > redmin) {
					red = (int) (red * adder);
				}
				if (red < redmin) {
					red = (red / adder > 255) ? 255 : (int) (red / adder);
				}

				if (green > greenmin) {
					green = (int) (green * adder);
				}
				if (green < greenmin) {
					green = (green / adder > 255) ? 255 : (int) (green / adder);
				}

				if (blue > bluemin) {
					blue = (int) (blue * adder);
				}
				if (blue < bluemin) {
					blue = (blue / adder > 255) ? 255 : (int) (blue / adder);
				}
			}

			rgbs[a] = (new Color(red, green, blue)).getRGB();
		}
	}

	public void bright(int percentage) {

		brightnessincrement = (percentage * 255) / 100;
		for (int a = 0; a < width * height; a++) {
			Color col = new Color(rgbs[a]);
			int red = col.getRed();
			int green = col.getGreen();
			int blue = col.getBlue();

			red = (red + brightnessincrement < 255) ? red + brightnessincrement
					: 255;
			green = (green + brightnessincrement < 255) ? green
					+ brightnessincrement : 255;
			blue = (blue + brightnessincrement < 255) ? blue
					+ brightnessincrement : 255;

			red = (red > 0) ? red : 0;
			green = (green > 0) ? green : 0;
			blue = (blue > 0) ? blue : 0;

			// System.out.println(" "+red+" "+green+" "+blue);
			col = new Color(red, green, blue);
			rgbs[a] = col.getRGB();

		}

	}

	public void adjustsize(int newwidth, int newheight) {

		double scalex = (newwidth * 1.0) / (width * 1.0);
		double scaley = (newheight * 1.0) / (height * 1.0);

		AffineTransform tx = new AffineTransform();

		tx.scale(scalex, scaley);
		tx.shear(0, 0);
		tx.translate(0, 0);
		tx.rotate(0);
		// System.out.println(""+tx.getDeterminant());

		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		BufferedImage buffe = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		buffe.setRGB(0, 0, width, height, rgbs, 0, width);
		buffe = op.filter(buffe, null);
		width = newwidth;
		height = newheight;
		rgbs = new int[width * height];
		buffe.getRGB(0, 0, width, height, rgbs, 0, width);

	}

	public void paintcrop(int mex, int mey, int mex2, int mey2, Graphics gracrop) {
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
		x1 = (mex < mex2) ? mex : mex2;
		x2 = (mex > mex2) ? mex : mex2;
		y1 = (mey < mey2) ? mey : mey2;
		y2 = (mey > mey2) ? mey : mey2;

		rectix = x1;
		rectfx = x2;
		rectiy = y1;
		rectfy = y2;
		int d = 4;
		// gracrop.setColor(Color.yellow);
		gracrop.setColor(new Color(200, 0, 200, 50));

		// gracrop.drawRect(x1,y1,x2-x1,y2-y1);

		gracrop.fillRect(x1, y1, x2 - x1, y2 - y1);
		gracrop.setColor(Color.yellow);
		gracrop.drawRect(x1, y1, x2 - x1, y2 - y1);
		gracrop.setColor(Color.green);
		// gracrop.fillRect(x1-d/2,y1-d/2,d,d);
		// gracrop.fillRect(x1-d/2,y2-d/2,d,d);
		// gracrop.fillRect(x2-d/2,y1-d/2,d,d);
		// gracrop.fillRect(x2-d/2,y2-d/2,d,d);
	}

	public void pencilsketch() {
		findedgessuperb();
		blackandwhite();
		invert();
	}

	public void encrypt() {

		int hashlen = password.length();
		int hashkey[] = new int[hashlen];
		for (int a = 0; a < hashlen; a++) {
			hashkey[a] = (int) password.charAt(a);
		}
		for (int a = 0; a < width * height; a++) {
			Color col = new Color(rgbs[a]);
			if (a % 2 == 0) {
				col = new Color(255 - col.getRed(), 255 - col.getGreen(),
						255 - col.getBlue());
			}
			rgbs[a] = col.getRGB();
		}

		for (int b = 0; b < 1; b++) {
			for (int a = 0; a < width * height; a++) {
				Color col = new Color(rgbs[a]);
				int red = col.getRed();
				int green = col.getGreen();
				int blue = col.getBlue();

				red = (red + hashkey[a % hashlen]) / 2;
				if (a % hashlen != 0) {
					green = (green + hashkey[a % hashlen - 1]) / 2;
				}
				if (a % hashlen != 0 && a % hashlen != 1) {
					blue = (blue + hashkey[a % hashlen - 2]) / 2;
				}
				if (a % hashlen == 0) {
					green = (green + hashkey[hashlen - 1]) / 2;
					blue = (blue + hashkey[hashlen - 2]) / 2;
				}
				if (a % hashlen == 1) {
					blue = (blue + hashkey[hashlen - 1]) / 2;
				}
				// if(a%2==0){red=255-red;green=255-green;blue=255-blue;}

				col = new Color(red, green, blue);
				rgbs[a] = col.getRGB();

			}
		}

	}

	public void decrypt() {

		int hashlen = password.length();
		int hashkey[] = new int[hashlen];
		for (int a = 0; a < hashlen; a++) {
			hashkey[a] = (int) password.charAt(a);
		}

		for (int b = 0; b < 1; b++) {
			for (int a = 0; a < width * height; a++) {
				Color col = new Color(rgbs[a]);
				int red = col.getRed();
				int green = col.getGreen();
				int blue = col.getBlue();

				red = (red * 2 - hashkey[a % hashlen]);
				if (a % hashlen != 0) {
					green = (green * 2 - hashkey[a % hashlen - 1]);
				}
				if (a % hashlen != 0 && a % hashlen != 1) {
					blue = (blue * 2 - hashkey[a % hashlen - 2]);
				}
				if (a % hashlen == 0) {
					green = (green * 2 - hashkey[hashlen - 1]);
					blue = (blue * 2 - hashkey[hashlen - 2]);
				}
				if (a % hashlen == 1) {
					blue = (blue * 2 - hashkey[hashlen - 1]);
				}

				red = (red < 0) ? 0 : red;
				green = (green < 0) ? 0 : green;
				blue = (blue < 0) ? 0 : blue;

				red = (red > 255) ? 255 : red;
				green = (green > 255) ? 255 : green;
				blue = (blue > 255) ? 255 : blue;

				// if(a%2==0){red=255-red;green=255-green;blue=255-blue;}

				// System.out.println(""+red+" "+red1+" "+green+" "+green1+" "+blue+" "+blue1+" "+hashkey[a%hashlen]);
				col = new Color(red, green, blue);
				rgbs[a] = col.getRGB();

			}
		}

		for (int a = 0; a < width * height; a++) {
			Color col = new Color(rgbs[a]);
			if (a % 2 == 0) {
				col = new Color(255 - col.getRed(), 255 - col.getGreen(),
						255 - col.getBlue());
			}
			rgbs[a] = col.getRGB();
		}

		// smoothing();
		// smoothing();
	}

	public void encrypt2() {
		String pass = "";
		for (int a = 0; a < password.length(); a++) {
			pass = pass + password.charAt(a);
		}
		int cycle = (width * height / 200 < 2000) ? 2000 : width * height / 200;
		cycle = (width * height / 2 > 2000) ? 2000 : width * height / 2;
		for (int a = 0; a < cycle; a++) {
			password = password + (char) ((int) (1000 * Math.tan(a)));
		}

		int hashlen = password.length();
		int hash[] = new int[hashlen];
		int mapbuffer[] = new int[hashlen];
		for (int a = 0; a < hashlen; a++) {
			hash[a] = (int) (password.charAt(a));
			mapbuffer[a] = (int) (password.charAt(a));
		}
		Arrays.sort(mapbuffer);
		int elements = 1;
		for (int a = 1; a < hashlen; a++) {
			if (mapbuffer[a] != mapbuffer[a - 1]) {
				elements++;
			}
		}

		int map[] = new int[elements];
		map[elements - 1] = mapbuffer[hashlen - 1];
		int index = 0;
		for (int a = 1; a < hashlen; a++) {
			if (mapbuffer[a] != mapbuffer[a - 1]) {
				map[index] = mapbuffer[a - 1];
				index++;
			}
		}
		for (int a = 0; a < hashlen; a++) {
			int nowel = hash[a];
			for (int b = a + 1; b < hashlen; b++) {
				if (nowel == hash[b]) {
					hash[a] = 0;
				}
			}
		}
		int hashmap[] = new int[elements];
		index = 0;
		for (int a = 0; a < hashlen; a++) {
			if (hash[a] != 0) {
				hashmap[index] = hash[a];
				index++;
			}
		}
		int hashfinale[] = new int[elements];
		for (int a = 0; a < elements; a++) {
			int nowel = hashmap[a];
			for (int b = 0; b < elements; b++) {
				if (nowel == map[b]) {
					hashfinale[a] = b;
				}
			}
		}
		int rgbs2[] = new int[width * height];

		for (int a = 0; a < width * height - elements; a = a + elements) {

			for (int b = a; b < a + elements; b++) {
				rgbs2[b] = rgbs[a + hashfinale[b - a]];
			}
			for (int b = a; b < a + elements; b++) {
				rgbs[b] = rgbs2[b];
			}
		}

		for (int a = 0; a < width * height; a++) {
			rgbs[a] = rgbs2[a];
		}
		password = pass;
	}

	public void decrypt2() {
		String pass = "";
		for (int a = 0; a < password.length(); a++) {
			pass = pass + password.charAt(a);
		}
		int cycle = (width * height / 200 < 2000) ? 2000 : width * height / 200;
		cycle = (width * height / 2 > 2000) ? 2000 : width * height / 2;
		for (int a = 0; a < cycle; a++) {
			password = password + (char) ((int) (1000 * Math.tan(a)));
		}

		int hashlen = password.length();
		int hash[] = new int[hashlen];
		int mapbuffer[] = new int[hashlen];
		for (int a = 0; a < hashlen; a++) {
			hash[a] = (int) (password.charAt(a));
			mapbuffer[a] = (int) (password.charAt(a));
		}
		Arrays.sort(mapbuffer);
		int elements = 1;
		for (int a = 1; a < hashlen; a++) {
			if (mapbuffer[a] != mapbuffer[a - 1]) {
				elements++;
			}
		}

		int map[] = new int[elements];
		map[elements - 1] = mapbuffer[hashlen - 1];
		int index = 0;
		for (int a = 1; a < hashlen; a++) {
			if (mapbuffer[a] != mapbuffer[a - 1]) {
				map[index] = mapbuffer[a - 1];
				index++;
			}
		}
		for (int a = 0; a < hashlen; a++) {
			int nowel = hash[a];
			for (int b = a + 1; b < hashlen; b++) {
				if (nowel == hash[b]) {
					hash[a] = 0;
				}
			}
		}
		int hashmap[] = new int[elements];
		index = 0;
		for (int a = 0; a < hashlen; a++) {
			if (hash[a] != 0) {
				hashmap[index] = hash[a];
				index++;
			}
		}
		int hashfinale[] = new int[elements];
		for (int a = 0; a < elements; a++) {
			int nowel = hashmap[a];
			for (int b = 0; b < elements; b++) {
				if (nowel == map[b]) {
					hashfinale[a] = b;
				}
			}
		}
		int hashfinalere[] = new int[elements];
		for (int a = 0; a < elements; a++) {
			hashfinalere[hashfinale[a]] = a;

		}

		int rgbs2[] = new int[width * height];
		for (int a = 0; a < width * height - elements; a = a + elements) {

			for (int b = a; b < a + elements; b++) {
				rgbs2[b] = rgbs[a + hashfinalere[b - a]];
			}
			for (int b = a; b < a + elements; b++) {
				rgbs[b] = rgbs2[b];
			}
		}
		for (int a = 0; a < width * height; a++) {
			rgbs[a] = rgbs2[a];
		}
		password = pass;

	}

	public void swapcolor(int r, int g, int b) {

		for (int a = 0; a < width * height; a++) {
			Color col = new Color(rgbs[a]);
			if (r == 0) {
				rgbs[a] = (new Color(col.getRed(), col.getBlue(),
						col.getGreen())).getRGB();
			}
			if (g == 0) {
				rgbs[a] = (new Color(col.getBlue(), col.getGreen(),
						col.getRed())).getRGB();
			}
			if (b == 0) {
				rgbs[a] = (new Color(col.getGreen(), col.getRed(),
						col.getBlue())).getRGB();
			}

		}
	}

	public void maximumfilter() {

		int a = 0;
		int w = width;
		int h = height;
		// int rgbsx[]=new int[w*h];
		for (a = 0; a < rgbs.length; a = a + 3) {
			if ((!((a < w) || (a > (w * (h - 1) - 1)) || (a % w == 0) || (((a + 1) % (w)) == 0)))
					&& ((a / w) % 3 == 0)) {

				Color col1 = new Color(rgbs[a - w - 1]);
				Color col2 = new Color(rgbs[a - w]);
				Color col3 = new Color(rgbs[a - w + 1]);
				Color col4 = new Color(rgbs[a - 1]);
				Color col5 = new Color(rgbs[a]);
				Color col6 = new Color(rgbs[a + 1]);
				Color col7 = new Color(rgbs[a + w - 1]);
				Color col8 = new Color(rgbs[a + w]);
				Color col9 = new Color(rgbs[a + w + 1]);
				int redmin = 0;
				int greenmin = 0;
				int bluemin = 0;
				redmin = col1.getRed();
				redmin = (col2.getRed() > redmin) ? col2.getRed() : redmin;
				redmin = (col3.getRed() > redmin) ? col3.getRed() : redmin;
				redmin = (col4.getRed() > redmin) ? col4.getRed() : redmin;
				redmin = (col5.getRed() > redmin) ? col5.getRed() : redmin;
				redmin = (col6.getRed() > redmin) ? col6.getRed() : redmin;
				redmin = (col7.getRed() > redmin) ? col7.getRed() : redmin;
				redmin = (col8.getRed() > redmin) ? col8.getRed() : redmin;
				redmin = (col9.getRed() > redmin) ? col9.getRed() : redmin;

				greenmin = col1.getGreen();
				greenmin = (col2.getGreen() > greenmin) ? col2.getGreen()
						: greenmin;
				greenmin = (col3.getGreen() > greenmin) ? col3.getGreen()
						: greenmin;
				greenmin = (col4.getGreen() > greenmin) ? col4.getGreen()
						: greenmin;
				greenmin = (col5.getGreen() > greenmin) ? col5.getGreen()
						: greenmin;
				greenmin = (col6.getGreen() > greenmin) ? col6.getGreen()
						: greenmin;
				greenmin = (col7.getGreen() > greenmin) ? col7.getGreen()
						: greenmin;
				greenmin = (col8.getGreen() > greenmin) ? col8.getGreen()
						: greenmin;
				greenmin = (col9.getGreen() > greenmin) ? col9.getGreen()
						: greenmin;

				bluemin = col1.getBlue();
				bluemin = (col2.getBlue() > bluemin) ? col2.getBlue() : bluemin;
				bluemin = (col3.getBlue() > bluemin) ? col3.getBlue() : bluemin;
				bluemin = (col4.getBlue() > bluemin) ? col4.getBlue() : bluemin;
				bluemin = (col5.getBlue() > bluemin) ? col5.getBlue() : bluemin;
				bluemin = (col6.getBlue() > bluemin) ? col6.getBlue() : bluemin;
				bluemin = (col7.getBlue() > bluemin) ? col7.getBlue() : bluemin;
				bluemin = (col8.getBlue() > bluemin) ? col8.getBlue() : bluemin;
				bluemin = (col9.getBlue() > bluemin) ? col9.getBlue() : bluemin;

				Color col = new Color(redmin, greenmin, bluemin);
				rgbs[a] = col.getRGB();
				rgbs[a - w - 1] = col.getRGB();
				rgbs[a - w] = col.getRGB();
				rgbs[a - w + 1] = col.getRGB();
				rgbs[a - 1] = col.getRGB();
				rgbs[a + 1] = col.getRGB();
				rgbs[a + w - 1] = col.getRGB();
				rgbs[a + w] = col.getRGB();
				rgbs[a + w + 1] = col.getRGB();
			}
		}
	}

	public void minimumfilter() {

		int a = 0;
		int w = width;
		int h = height;
		// int rgbsx[]=new int[w*h];
		for (a = 0; a < rgbs.length; a = a + 3) {
			if ((!((a < w) || (a > (w * (h - 1) - 1)) || (a % w == 0) || (((a + 1) % (w)) == 0)))
					&& ((a / w) % 3 == 0)) {

				Color col1 = new Color(rgbs[a - w - 1]);
				Color col2 = new Color(rgbs[a - w]);
				Color col3 = new Color(rgbs[a - w + 1]);
				Color col4 = new Color(rgbs[a - 1]);
				Color col5 = new Color(rgbs[a]);
				Color col6 = new Color(rgbs[a + 1]);
				Color col7 = new Color(rgbs[a + w - 1]);
				Color col8 = new Color(rgbs[a + w]);
				Color col9 = new Color(rgbs[a + w + 1]);
				int redmin = 0;
				int greenmin = 0;
				int bluemin = 0;
				redmin = col1.getRed();
				redmin = (col2.getRed() < redmin) ? col2.getRed() : redmin;
				redmin = (col3.getRed() < redmin) ? col3.getRed() : redmin;
				redmin = (col4.getRed() < redmin) ? col4.getRed() : redmin;
				redmin = (col5.getRed() < redmin) ? col5.getRed() : redmin;
				redmin = (col6.getRed() < redmin) ? col6.getRed() : redmin;
				redmin = (col7.getRed() < redmin) ? col7.getRed() : redmin;
				redmin = (col8.getRed() < redmin) ? col8.getRed() : redmin;
				redmin = (col9.getRed() < redmin) ? col9.getRed() : redmin;

				greenmin = col1.getGreen();
				greenmin = (col2.getGreen() < greenmin) ? col2.getGreen()
						: greenmin;
				greenmin = (col3.getGreen() < greenmin) ? col3.getGreen()
						: greenmin;
				greenmin = (col4.getGreen() < greenmin) ? col4.getGreen()
						: greenmin;
				greenmin = (col5.getGreen() < greenmin) ? col5.getGreen()
						: greenmin;
				greenmin = (col6.getGreen() < greenmin) ? col6.getGreen()
						: greenmin;
				greenmin = (col7.getGreen() < greenmin) ? col7.getGreen()
						: greenmin;
				greenmin = (col8.getGreen() < greenmin) ? col8.getGreen()
						: greenmin;
				greenmin = (col9.getGreen() < greenmin) ? col9.getGreen()
						: greenmin;

				bluemin = col1.getBlue();
				bluemin = (col2.getBlue() < bluemin) ? col2.getBlue() : bluemin;
				bluemin = (col3.getBlue() < bluemin) ? col3.getBlue() : bluemin;
				bluemin = (col4.getBlue() < bluemin) ? col4.getBlue() : bluemin;
				bluemin = (col5.getBlue() < bluemin) ? col5.getBlue() : bluemin;
				bluemin = (col6.getBlue() < bluemin) ? col6.getBlue() : bluemin;
				bluemin = (col7.getBlue() < bluemin) ? col7.getBlue() : bluemin;
				bluemin = (col8.getBlue() < bluemin) ? col8.getBlue() : bluemin;
				bluemin = (col9.getBlue() < bluemin) ? col9.getBlue() : bluemin;

				Color col = new Color(redmin, greenmin, bluemin);
				rgbs[a] = col.getRGB();
				rgbs[a - w - 1] = col.getRGB();
				rgbs[a - w] = col.getRGB();
				rgbs[a - w + 1] = col.getRGB();
				rgbs[a - 1] = col.getRGB();
				rgbs[a + 1] = col.getRGB();
				rgbs[a + w - 1] = col.getRGB();
				rgbs[a + w] = col.getRGB();
				rgbs[a + w + 1] = col.getRGB();
			}
		}
	}

	public void booleannot() {
		int w = width;
		int h = height;
		for (int a = 0; a < w * h; a++) {
			Color col = new Color(rgbs[a]);
			int red = col.getRed();
			int green = col.getGreen();
			int blue = col.getBlue();

			String redb = "";
			String greenb = "";
			String blueb = "";
			int newred = 0;
			int newgreen = 0;
			int newblue = 0;

			for (int x = 0; x < 8; x++) {
				int power = (int) Math.pow(2, x);

				newred = newred + (1 - red % 2) * power;
				red = red / 2;

				newgreen = newgreen + (1 - green % 2) * power;
				green = green / 2;

				newblue = newblue + (1 - blue % 2) * power;
				blue = blue / 2;

			}

			col = new Color(newred, newgreen, newblue);
			rgbs[a] = col.getRGB();

		}
	}

	public void findedgessuperb() {
		int w = width;
		int h = height;
		int rgbs2[] = new int[w * h];
		int redavg = 0;
		int greenavg = 0;
		int blueavg = 0;
		double kernel[] = { -1.0, -1.414, -1.0, 0.0, 0.0, 0.0, 1.0, 1.414, 1.0 };
		double kernel2[] = { 1.0, 0.0, -1.0, 1.414, 0.0, -1.414, 1.0, 0.0, -1.0 };

		double sum1 = 0;
		double possum1 = 0;
		double negsum1 = 0;
		for (int x = 0; x < 9; x++) {
			if (kernel[x] > 0) {
				possum1 = possum1 + kernel[x];
			}
			if (kernel[x] < 0) {
				negsum1 = negsum1 + kernel[x];
			}
			sum1 = sum1 + Math.abs(kernel[x]);
		}

		double sum2 = 0;
		double possum2 = 0;
		double negsum2 = 0;
		for (int x = 0; x < 9; x++) {
			if (kernel[x] > 0) {
				possum2 = possum2 + kernel[x];
			}
			if (kernel[x] < 0) {
				negsum2 = negsum2 + kernel[x];
			}
			sum2 = sum2 + Math.abs(kernel[x]);
		}

		sum1 = (possum1 > Math.abs(negsum1)) ? possum1 : Math.abs(negsum1);
		sum2 = (possum2 > Math.abs(negsum2)) ? possum2 : Math.abs(negsum2);
		Color col[] = new Color[9];
		for (int a = 0; a < rgbs.length; a++) {
			if (!((a < w) || (a > (w * (h - 1) - 1)) || (a % w == 0) || ((a + 1) % (w)) == 0)) {

				col[0] = new Color(rgbs[a - w - 1]);
				col[1] = new Color(rgbs[a - w]);
				col[2] = new Color(rgbs[a - w + 1]);
				col[3] = new Color(rgbs[a - 1]);
				col[4] = new Color(rgbs[a]);
				col[5] = new Color(rgbs[a + 1]);
				col[6] = new Color(rgbs[a + w - 1]);
				col[7] = new Color(rgbs[a + w]);
				col[8] = new Color(rgbs[a + w + 1]);

				double red1 = 0;
				double green1 = 0;
				double blue1 = 0;

				double red2 = 0;
				double green2 = 0;
				double blue2 = 0;

				for (int q = 0; q < 9; q++) {
					red1 = red1 + col[q].getRed() * kernel[q];
					green1 = green1 + col[q].getGreen() * kernel[q];
					blue1 = blue1 + col[q].getBlue() * kernel[q];

					red2 = red2 + col[q].getRed() * kernel2[q];
					green2 = green2 + col[q].getGreen() * kernel2[q];
					blue2 = blue2 + col[q].getBlue() * kernel2[q];

				}
				red1 = (int) (red1 / sum1);
				green1 = (int) (green1 / sum1);
				blue1 = (int) (blue1 / sum1);

				red2 = (int) (red2 / sum2);
				green2 = (int) (green2 / sum2);
				blue2 = (int) (blue2 / sum2);

				red1 = (red1 < 0) ? Math.abs(red1) : red1;
				green1 = (green1 < 0) ? Math.abs(green1) : green1;
				blue1 = (blue1 < 0) ? Math.abs(blue1) : blue1;

				red1 = (red1 > 255) ? 255 : red1;
				green1 = (green1 > 255) ? 255 : green1;
				blue1 = (blue1 > 255) ? 255 : blue1;

				red1 = (red1 < 0) ? Math.abs(red2) : red1;
				green1 = (green1 < 0) ? Math.abs(green2) : green1;
				blue1 = (blue1 < 0) ? Math.abs(blue2) : blue1;

				red2 = (red2 > 255) ? 255 : red2;
				green2 = (green2 > 255) ? 255 : green2;
				blue2 = (blue2 > 255) ? 255 : blue2;

				double red = (red1 > red2) ? red1 : red2;
				double green = (green1 > green2) ? green1 : green2;
				double blue = (blue1 > blue2) ? blue1 : blue2;

				redavg = redavg + (int) red;
				greenavg = greenavg + (int) green;
				blueavg = blueavg + (int) blue;

				Color colf = new Color((int) red, (int) green, (int) blue);

				rgbs2[a] = colf.getRGB();

			}
		}
		redavg = redavg / (w * h);
		greenavg = greenavg / (w * h);
		blueavg = blueavg / (w * h);
		double cfa = 5;

		for (int a = 0; a < width * height; a++) {
			Color colex = new Color(rgbs2[a]);
			int red = colex.getRed();
			int green = colex.getGreen();
			int blue = colex.getBlue();

			if (red > redavg) {
				red = (red * cfa > 255) ? 255 : (int) (red * cfa);
			}
			if (red < redavg) {
				red = (int) (red / cfa);
			}

			if (green > greenavg) {
				green = (green * cfa > 255) ? 255 : (int) (green * cfa);
			}
			if (green < greenavg) {
				green = (int) (green / cfa);
			}

			if (blue > blueavg) {
				blue = (blue * cfa > 255) ? 255 : (int) (blue * cfa);
			}
			if (blue < blueavg) {
				blue = (int) (blue / cfa);
			}

			rgbs2[a] = (new Color(red, green, blue)).getRGB();
		}

		for (int a = 0; a < width * height; a++) {
			rgbs[a] = rgbs2[a];
		}
	}

	public void crop() {
		int initialx = (int) (rectix / imagescaleratio);
		int initialy = (int) (rectiy / imagescaleratio);
		int finalx = (int) (rectfx / imagescaleratio);
		int finaly = (int) (rectfy / imagescaleratio);
		int w = finalx - initialx + 1;
		int h = finaly - initialy + 1;
		BufferedImage buffer1 = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		BufferedImage buffer2 = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		buffer1.setRGB(0, 0, width, height, rgbs, 0, width);
		buffer2 = buffer1.getSubimage(initialx, initialy, w, h);
		width = w;
		height = h;
		rgbs = new int[w * h];
		buffer2.getRGB(0, 0, width, height, rgbs, 0, width);

	}

	public void transpose() {
		int w = width;
		int h = height;
		int rgbsx[] = new int[w * h];
		for (int a = 0; a < width; a++) {
			for (int b = 0; b < height; b++) {
				rgbsx[b + a * height] = rgbs[b * width + a];
			}
		}
		for (int a = 0; a < width * height; a++) {
			rgbs[a] = rgbsx[a];
		}
		width = height;
		height = w;

	}

	public BufferedImage copybuffimage(BufferedImage x) {
		AffineTransform tx = new AffineTransform();

		tx.scale(1.0, 1.0);
		tx.shear(0, 0);
		tx.translate(0, 0);
		tx.rotate(0.0);

		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		x = op.filter(x, null);
		return x;

	}

	public void emboss() {
		int w = width;
		int h = height;
		int rgbs2[] = new int[w * h];
		int redavg = 0;
		int greenavg = 0;
		int blueavg = 0;
		double kernel[] = { -1.0, -1.414, -1.0, 0.0, 0.0, 0.0, 1.0, 1.414, 1.0 };
		double kernel2[] = { 1.0, 0.0, -1.0, 1.414, 0.0, -1.414, 1.0, 0.0, -1.0 };

		double sum1 = 0;
		double possum1 = 0;
		double negsum1 = 0;
		for (int x = 0; x < 9; x++) {
			if (kernel[x] > 0) {
				possum1 = possum1 + kernel[x];
			}
			if (kernel[x] < 0) {
				negsum1 = negsum1 + kernel[x];
			}
			sum1 = sum1 + Math.abs(kernel[x]);
		}

		double sum2 = 0;
		double possum2 = 0;
		double negsum2 = 0;
		for (int x = 0; x < 9; x++) {
			if (kernel[x] > 0) {
				possum2 = possum2 + kernel[x];
			}
			if (kernel[x] < 0) {
				negsum2 = negsum2 + kernel[x];
			}
			sum2 = sum2 + Math.abs(kernel[x]);
		}

		sum1 = (possum1 > Math.abs(negsum1)) ? possum1 : Math.abs(negsum1);
		sum2 = (possum2 > Math.abs(negsum2)) ? possum2 : Math.abs(negsum2);
		Color col[] = new Color[9];
		for (int a = 0; a < rgbs.length; a++) {
			if (!((a < w) || (a > (w * (h - 1) - 1)) || (a % w == 0) || ((a + 1) % (w)) == 0)) {

				col[0] = new Color(rgbs[a - w - 1]);
				col[1] = new Color(rgbs[a - w]);
				col[2] = new Color(rgbs[a - w + 1]);
				col[3] = new Color(rgbs[a - 1]);
				col[4] = new Color(rgbs[a]);
				col[5] = new Color(rgbs[a + 1]);
				col[6] = new Color(rgbs[a + w - 1]);
				col[7] = new Color(rgbs[a + w]);
				col[8] = new Color(rgbs[a + w + 1]);

				double red1 = 0;
				double green1 = 0;
				double blue1 = 0;

				double red2 = 0;
				double green2 = 0;
				double blue2 = 0;

				for (int q = 0; q < 9; q++) {
					red1 = red1 + col[q].getRed() * kernel[q];
					green1 = green1 + col[q].getGreen() * kernel[q];
					blue1 = blue1 + col[q].getBlue() * kernel[q];

					red2 = red2 + col[q].getRed() * kernel2[q];
					green2 = green2 + col[q].getGreen() * kernel2[q];
					blue2 = blue2 + col[q].getBlue() * kernel2[q];

				}
				red1 = (red1 / sum1);
				green1 = (green1 / sum1);
				blue1 = (blue1 / sum1);

				red2 = (red2 / sum2);
				green2 = (green2 / sum2);
				blue2 = (blue2 / sum2);

				red1 = (red1 + 255) / 2;
				red2 = (red2 + 255) / 2;
				green1 = (green1 + 255) / 2;
				green2 = (green2 + 255) / 2;
				blue1 = (blue1 + 255) / 2;
				blue2 = (blue2 + 255) / 2;

				int red = (int) ((red1 + red2) / 2);
				int green = (int) ((green1 + green2) / 2);
				int blue = (int) ((blue1 + blue2) / 2);

				redavg = redavg + (int) red;
				greenavg = greenavg + (int) green;
				blueavg = blueavg + (int) blue;

				Color colf = new Color((int) red, (int) green, (int) blue);

				rgbs2[a] = colf.getRGB();

			}
		}
		redavg = redavg / (w * h);
		greenavg = greenavg / (w * h);
		blueavg = blueavg / (w * h);
		double cfa = 8;

		/*
		 * for(int a=0 ;a < width*height;a++){ Color colex=new Color(rgbs2[a]);
		 * int red=colex.getRed(); int green=colex.getGreen(); int
		 * blue=colex.getBlue();
		 * 
		 * if(red>redavg){red=(red*cfa>255)?255:(int)(red*cfa);}
		 * if(red<redavg){red=(int)(red/cfa);}
		 * 
		 * if(green>greenavg){green=(green*cfa>255)?255:(int)(green*cfa);}
		 * if(green<greenavg){green=(int)(green/cfa);}
		 * 
		 * if(blue>blueavg){blue=(blue*cfa>255)?255:(int)(blue*cfa);}
		 * if(blue<blueavg){blue=(int)(blue/cfa);}
		 * 
		 * rgbs2[a]=(new Color(red,green,blue)).getRGB(); }
		 */

		for (int a = 0; a < width * height; a++) {
			rgbs[a] = rgbs2[a];
		}
	}

	public void cutimagepart() {
		for (int a = 0; a < width * height; a++) {
			rgbs[a] = (Color.white).getRGB();

		}

	}

	/*
	 * public void anycolorswap(double r1,double g1,double b1,double r2,double
	 * g2,double b2){
	 * System.out.println(" "+r1+" "+g1+" "+b1+" "+r2+" "+g2+" "+b2) ; double
	 * mag1=Math.sqrt(r1*r1+g1*g1+b1*b1); double
	 * mag2=Math.sqrt(r2*r2+g2*g2+b2*b2);
	 * 
	 * for(int a=0;a<rgbs.length;a++){
	 * 
	 * Color col=new Color(rgbs[a]);
	 * 
	 * double r=col.getRed()*1.0; double g=col.getGreen()*1.0; double
	 * b=col.getBlue()*1.0;
	 * 
	 * double
	 * rfinal=(r+(-((r*r1+g*g1+b*b1)/mag1)+((r*r2+g*g2+b*b2)/mag2))*(r1/mag1
	 * )+(-((r*r1+g*g1+b*b1)/mag2)+((r*r2+g*g2+b*b2)/mag1))*(r2/mag2)); double
	 * gfinal
	 * =(g+(-((r*r1+g*g1+b*b1)/mag1)+((r*r2+g*g2+b*b2)/mag2))*(g1/mag1)+(-(
	 * (r*r1+g*g1+b*b1)/mag2)+((r*r2+g*g2+b*b2)/mag1))*(g2/mag2)); double
	 * bfinal=
	 * (b+(-((r*r1+g*g1+b*b1)/mag1)+((r*r2+g*g2+b*b2)/mag2))*(b1/mag1)+(-(
	 * (r*r1+g*g1+b*b1)/mag2)+((r*r2+g*g2+b*b2)/mag1))*(b2/mag2));
	 * //System.out.println
	 * (" "+rfinal+" "+gfinal+" "+bfinal+"   "+r+" "+g+" "+b) ;
	 * rfinal=Math.abs(rfinal); gfinal=Math.abs(gfinal);
	 * bfinal=Math.abs(bfinal);
	 * 
	 * rfinal=(rfinal<0)?0:rfinal; gfinal=(gfinal<0)?0:gfinal;
	 * bfinal=(bfinal<0)?0:bfinal;
	 * 
	 * rfinal=(rfinal>255)?255:rfinal; gfinal=(gfinal>255)?255:gfinal;
	 * bfinal=(bfinal>255)?255:bfinal;
	 * 
	 * col=new Color((int)rfinal,(int)gfinal,(int)bfinal);
	 * 
	 * rgbs[a]=col.getRGB(); } }
	 */

	public void anycolorswap(double r1, double g1, double b1, double r2,
			double g2, double b2) {

		double mag1 = Math.sqrt(r1 * r1 + g1 * g1 + b1 * b1);
		double mag2 = Math.sqrt(r2 * r2 + g2 * g2 + b2 * b2);
		double megamag = Math.sqrt((g1 * b2 - b1 * g2) * (g1 * b2 - b1 * g2)
				+ (b1 * r2 - r1 * b2) * (b1 * r2 - r1 * b2)
				+ (r1 * g2 - g1 * r2) * (r1 * g2 - g1 * r2));

		for (int a = 0; a < rgbs.length; a++) {

			Color col = new Color(rgbs[a]);

			double r = col.getRed() * 1.0;
			double g = col.getGreen() * 1.0;
			double b = col.getBlue() * 1.0;

			double megacomp = (r * g1 * b2 - r * b1 * g2 + g * b1 * r2 - g * r1
					* b2 + b * r1 * g2 - b * g1 * r2);
			double rfinal = (((r * r1 + g * g1 + b * b1) * r2 + (r * r2 + g
					* g2 + b * b2)
					* r1) / (mag1 * mag2))
					+ (megacomp * (g1 * b2 - b1 * g2)) / (megamag * megamag);
			double gfinal = (((r * r1 + g * g1 + b * b1) * g2 + (r * r2 + g
					* g2 + b * b2)
					* g1) / (mag1 * mag2))
					+ (megacomp * (b1 * r2 - r1 * b2)) / (megamag * megamag);
			double bfinal = (((r * r1 + g * g1 + b * b1) * b2 + (r * r2 + g
					* g2 + b * b2)
					* b1) / (mag1 * mag2))
					+ (megacomp * (r1 * g2 - r2 * g1)) / (megamag * megamag);
			// double
			// rfinal=(((r*r1+g*g1+b*b1)*r2+(r*r2+g*g2+b*b2)*r1)/(mag1*mag2));
			// double
			// gfinal=(((r*r1+g*g1+b*b1)*g2+(r*r2+g*g2+b*b2)*g1)/(mag1*mag2));
			// double
			// bfinal=(((r*r1+g*g1+b*b1)*b2+(r*r2+g*g2+b*b2)*b1)/(mag1*mag2));
			// System.out.println(""+(int)rfinal+","+(int)gfinal+","+(int)bfinal+" "+r+","+g+","+b);
			rfinal = (rfinal < 0) ? 0 : rfinal;
			gfinal = (gfinal < 0) ? 0 : gfinal;
			bfinal = (bfinal < 0) ? 0 : bfinal;

			rfinal = (rfinal > 255) ? 255 : rfinal;
			gfinal = (gfinal > 255) ? 255 : gfinal;
			bfinal = (bfinal > 255) ? 255 : bfinal;

			col = new Color((int) rfinal, (int) gfinal, (int) bfinal);

			rgbs[a] = col.getRGB();

		}
	}

	public void contraststretching(double cutoff) {

		int a = 0, b = 255, c = 0, d = 0;
		int lowint = 0;
		int upint = 0;
		int num = width * height;
		int intense[] = new int[256];
		int pixelmax = 0;
		int index = 0;

		for (int x = 0; x < rgbs.length; x++) {
			Color col = new Color(rgbs[x]);
			int intensity = col.getRed() + col.getGreen() + col.getBlue();
			intense[intensity / 3]++;
		}

		for (int x = 0; x < 256; x++) {
			int old = pixelmax;
			pixelmax = (intense[x] > pixelmax) ? intense[x] : pixelmax;
			if (pixelmax != old) {
				index = x;
			}
		}

		int cutoffmag = (int) ((pixelmax * cutoff * 1.0) / 100.0);

		boolean breaker = true;
		upint = index;
		lowint = index;
		for (int x = 0; x < index && breaker; x++) {
			if (intense[x] >= cutoffmag) {
				lowint = x;
				breaker = false;
			}

		}

		breaker = true;
		for (int x = 255; x > index && breaker; x--) {
			if (intense[x] >= cutoffmag) {
				upint = x;
				breaker = false;
			}

		}

		c = lowint;
		d = upint;

		for (int x = 0; x < rgbs.length; x++) {
			Color col = new Color(rgbs[x]);
			int intensity = (col.getRed() + col.getGreen() + col.getBlue()) / 3;
			int red = col.getRed();
			int green = col.getGreen();
			int blue = col.getBlue();

			red = (red - c) * ((b - a) / (d - c)) + a;
			green = (green - c) * ((b - a) / (d - c)) + a;
			blue = (blue - c) * ((b - a) / (d - c)) + a;

			red = (red < 0) ? 0 : red;
			green = (green < 0) ? 0 : green;
			blue = (blue < 0) ? 0 : blue;

			red = (red > 255) ? 255 : red;
			green = (green > 255) ? 255 : green;
			blue = (blue > 255) ? 255 : blue;

			col = new Color(red, green, blue);
			rgbs[x] = col.getRGB();

		}
	}

	public void histogramequalisation() {
		int w = width;
		int h = height;
		int numpix[] = new int[256];
		for (int a = 0; a < w * h; a++) {
			Color col = new Color(rgbs[a]);
			int intense = (col.getRed() + col.getGreen() + col.getBlue()) / 3;
			numpix[intense]++;
		}
		int numk[] = new int[256];
		for (int a = 0; a < 256; a++) {
			for (int b = 0; b <= a; b++) {
				numk[a] = numk[a] + numpix[b];
			}

		}
		double newd[] = new double[256];
		for (int a = 0; a < 256; a++) {
			double x = (numk[a] * 1.0 * 255) / (w * h * 1.0);

			// System.out.println(x+" "+numk[a]+" "+numpix[a]);
			x = (x < 0) ? 0 : x;

			newd[a] = x;
		}

		for (int a = 0; a < w * h; a++) {
			Color col = new Color(rgbs[a]);
			int red = col.getRed();
			int green = col.getGreen();
			int blue = col.getBlue();
			int intensity = (red + green + blue) / 3;

			double ratio = newd[intensity] / intensity;
			red = (int) (red * ratio);
			green = (int) (green * ratio);
			blue = (int) (blue * ratio);

			red = (red < 0) ? 0 : red;
			green = (green < 0) ? 0 : green;
			blue = (blue < 0) ? 0 : blue;

			red = (red > 255) ? 255 : red;
			green = (green > 255) ? 255 : green;
			blue = (blue > 255) ? 255 : blue;

			col = new Color(red, green, blue);
			rgbs[a] = col.getRGB();
		}
		// http://homepages.inf.ed.ac.uk/rbf/HIPR2/histeq.htm#1

	}

	public void gammacorrection(double basis) {

		double maxred = 0;
		double maxgreen = 0;
		double maxblue = 0;

		for (int a = 0; a < width * height; a++) {
			Color col = new Color(rgbs[a]);

			double red = col.getRed();
			double green = col.getGreen();
			double blue = col.getBlue();

			maxred = (maxred < red) ? red : maxred;
			maxgreen = (maxgreen < green) ? green : maxgreen;
			maxblue = (maxblue < blue) ? blue : maxblue;
		}
		maxred = Math.pow(basis, maxred);
		maxgreen = Math.pow(basis, maxgreen);
		maxblue = Math.pow(basis, maxblue);

		double max = (maxred > maxgreen) ? maxred : maxgreen;
		max = (maxblue > max) ? maxblue : max;

		double ratio = 255.0 / max;

		for (int a = 0; a < width * height; a++) {

			Color col = new Color(rgbs[a]);

			double red = col.getRed();
			double green = col.getGreen();
			double blue = col.getBlue();

			red = (ratio * Math.pow(basis, red) - ratio);
			green = (ratio * Math.pow(basis, green) - ratio);
			blue = (ratio * Math.pow(basis, blue) - ratio);
			red = (red < 0) ? 0 : red;
			green = (green < 0) ? 0 : green;
			blue = (blue < 0) ? 0 : blue;

			red = (red > 255) ? 255 : red;
			green = (green > 255) ? 255 : green;
			blue = (blue > 255) ? 255 : blue;
			col = new Color((int) red, (int) green, (int) blue);

			rgbs[a] = col.getRGB();

		}
	}

	public void makecolor(int turn) {
		for (int a = 0; a < width * height; a++) {
			Color col = new Color(rgbs[a]);

			int r = col.getRed();
			int g = col.getGreen();
			int b = col.getBlue();
			if (turn == 0) {
				col = new Color(r, r, r);
			}
			if (turn == 1) {
				col = new Color(r, r, g);
			}
			if (turn == 2) {
				col = new Color(r, r, b);
			}
			if (turn == 3) {
				col = new Color(r, g, r);
			}
			if (turn == 4) {
				col = new Color(r, g, g);
			}
			if (turn == 5) {
				col = new Color(r, g, b);
			}
			if (turn == 6) {
				col = new Color(r, b, r);
			}
			if (turn == 7) {
				col = new Color(r, b, g);
			}
			if (turn == 8) {
				col = new Color(r, b, b);
			}
			if (turn == 9) {
				col = new Color(g, r, r);
			}
			if (turn == 10) {
				col = new Color(g, r, g);
			}
			if (turn == 11) {
				col = new Color(g, r, b);
			}
			if (turn == 12) {
				col = new Color(g, g, r);
			}
			if (turn == 13) {
				col = new Color(g, g, g);
			}
			if (turn == 14) {
				col = new Color(g, g, b);
			}
			if (turn == 15) {
				col = new Color(g, b, r);
			}
			if (turn == 16) {
				col = new Color(g, b, g);
			}
			if (turn == 17) {
				col = new Color(g, b, b);
			}
			if (turn == 18) {
				col = new Color(b, r, r);
			}
			if (turn == 19) {
				col = new Color(b, r, g);
			}
			if (turn == 20) {
				col = new Color(b, r, b);
			}
			if (turn == 21) {
				col = new Color(b, g, r);
			}
			if (turn == 22) {
				col = new Color(b, g, g);
			}
			if (turn == 23) {
				col = new Color(b, g, b);
			}
			if (turn == 24) {
				col = new Color(b, b, r);
			}
			if (turn == 25) {
				col = new Color(b, b, g);
			}
			if (turn == 26) {
				col = new Color(b, b, b);
			}

			rgbs[a] = col.getRGB();
		}

	}

	public void function() {
		for (int a = 0; a < width * height; a++) {
			Color col = new Color(rgbs[a]);

			int r = col.getRed();
			int g = col.getGreen();
			int b = col.getBlue();
			if (r > g & r > b) {
				Color cole = new Color(255, 0, 0);
				;
				rgbs[a] = cole.getRGB();
			}

		}
	}

}

