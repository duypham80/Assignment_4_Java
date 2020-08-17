/*
 * Course Name: CST8284_303
 * Student Name: Duy Pham
 * Class Name: RegViewGUI
 * Date: Aug 09, 2020
 * 
 */

/* Assignment 4 GUI code provided by Prof. D. Houtman
 * for use in CST8284 Assignment 4, due August 7, 2020.
 * This code is for one-time use only during the Summer 2020 semester.
 * (c) D. Houtman.  All rights reserved
 */

package cst8284.asgmt4.landRegistry;

    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.Font;
    import java.awt.GridBagConstraints;
    import java.awt.GridBagLayout;
    import java.awt.GridLayout;
    import java.awt.Insets;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.ItemEvent;
    import java.awt.event.ItemListener;
    import java.io.File;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.Scanner;

    import javax.swing.BorderFactory;
    import javax.swing.JButton;
    import javax.swing.JComboBox;
    import javax.swing.JDialog;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
    import javax.swing.JOptionPane;
    import javax.swing.JPanel;
    import javax.swing.JScrollPane;
    import javax.swing.JTextField;
    import javax.swing.SwingUtilities;

public final class RegViewGUI extends JFrame {

  // gridx    gridy                  gridwidth  gridheight  weightx weighty anchor                       fill                           insets                      ipadx  ipady
  private static final GridBagConstraints registrantConstraints = new GridBagConstraints(
      0,       0,                     1,         1,          0.5,     0,     GridBagConstraints.NORTH,    GridBagConstraints.VERTICAL,   new Insets(10, 10, 10, 10), 0,     0);
  private static final GridBagConstraints btnConstraints = new GridBagConstraints(
      1,       0,                     1,         2,          0.5,     0.5,   GridBagConstraints.NORTH,    GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5),   0,     0);
  private static final GridBagConstraints propertyConstraints = new GridBagConstraints(
      0,       1,                     1,         1,          0.5,     1,	  GridBagConstraints.NORTH,    GridBagConstraints.VERTICAL,   new Insets(5, 5, 5, 5),   0,     0);
  private static final GridBagConstraints southBtnConstraints = new GridBagConstraints(
      0,       2,                     1,         1,          0.5,     1,     GridBagConstraints.SOUTH,    GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5),   0,     0);
  private static final GridBagConstraints exitBtnConstraints = new GridBagConstraints(
      1,       2,                     1,         1,          0.5,     1,	  GridBagConstraints.SOUTH,    GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5),   0,     0);

  private static final int     SPACE = 12, BORDER = 24;
  private static final boolean ON = true,  OFF = false;

  // eastBtnPanel values
  private static final int ADD_NEW_REGISTRANT = 1, DELETE_REGISTRANT = 2, ADD_PROPERTY = 3,
      DELETE_CURRENT_PROPERTY = 4, CHANGE_REGISTRATION_NUMBER = 5;
  // southBtn Panel values
  private static final int RELOAD_LAND_REGISTRY = 1, BACKUP_LAND_REGISTRY = 2;

  private static final Font      defaultFont         = new Font("SansSerif", Font.PLAIN, 20);
  private static final Dimension defaultDimension    = new Dimension(540, 40);
  private static final Dimension mainDimension       = new Dimension(1024, 540);

  private static       JFrame    f                   = new JFrame("");
  private static final JPanel    mainPanel           = new JPanel(new GridBagLayout());
  private static       JPanel    registrantsPanel    = new JPanel();
  private static       JPanel    eastBtnPanel        = new JPanel();
  private static       JPanel    propertiesPanel     = new JPanel();
  private static       JPanel    southBtnPanel       = new JPanel();
  private static       JComboBox<String> regNumBox;
  private static       JScrollPane regScrollPane;
  private static       JScrollPane propScrollPane;


  /**
   * Declare the RegControl object
   */
  private static RegControl rc = new RegControl();

  /**
   * Declare the two file names of Property and Registrant
   */
  public static final String PROPERTIES_FILE = "LandRegistry.prop";
  public static final String REGISTRANTS_FILE = "LandRegistry.reg";


  public static void launchDialog(){
    //------------------------------------------------------------------------------------------------------//
    f.setTitle("Land Registry");                                                              // - O X //
    //------------------------------------------------------ -----------------------------------------------//
    //                                                                            //                        //
    mainPanel.add(loadRegistrantsPanel(getRegControl().listOfRegistrants()),registrantConstraints);         //                        //
    mainPanel.add(       //
        loadEastBtnPanel(),  //
        btnConstraints);     //
    //                                                                            //                        //
    //----------------------------------------------------------------------------//                        //
    //                                                                            //                        //
    mainPanel.add(loadPropertyPanel(null), propertyConstraints);              //                        //
    //                                                                            //                        //
    //------------------------------------------------------------------------------------------------------//
    //                                                                                                      //
    mainPanel.add(loadSouthBtnPanel(), southBtnConstraints);    //        EXIT            //
    //                                                                                                      //
    //------------------------------------------------------------------------------------------------------//

    mainPanel.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
    mainPanel.setPreferredSize(mainDimension);
    if (!new File(REGISTRANTS_FILE).exists()) {
      turnAllBtnsIn(southBtnPanel, false, EXIT_ON_CLOSE);
    };

    f.add(mainPanel);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.pack();
    f.setVisible(true);
  }

  private static JPanel loadRegistrantsPanel(ArrayList<Registrant> regs) {

    registrantsPanel.setPreferredSize(getStandardDimension(10, 192));
    registrantsPanel.setBorder(BorderFactory.createLineBorder(Color.black));

    JComboBox<String> cBox = new JComboBox<String>();
    cBox.setFont(defaultFont);
    cBox.setPreferredSize(getStandardDimension(-5,10));
    if (regs != null && regs.size() > 0) {
      cBox.addItem("All Registration Numbers");
      for (Registrant reg: regs)
        cBox.addItem(Integer.toString(reg.getRegNum()));
    }
    // Save to variable regNumBox for later use (get current choice)
    regNumBox = cBox;

    JPanel cBoxPanel = new JPanel();
    cBoxPanel.add(cBox);
    registrantsPanel.add(cBoxPanel);

    JScrollPane regScrollPanel = new JScrollPane(getRestrantsScrollPanel(regs)) ;
    // Save to variable regScrollPane for later use (reload scroll)
    regScrollPane = regScrollPanel;
    regScrollPanel.setPreferredSize(getStandardDimension(10,120));
    registrantsPanel.add(regScrollPanel);
    return registrantsPanel;
  }

  private static JPanel loadEastBtnPanel() {
    eastBtnPanel.setLayout(new GridLayout(5, 1, SPACE, SPACE));
    eastBtnPanel.add(loadBtn("    Add New Registrant    ", 'l', true, addRegistrantActionListener));  // TODO: replace null with ActionListener to call dialog that prompts for new registrant's name
    eastBtnPanel.add(loadBtn("    Delete Registrant     ", 's', false, deleteRegistrantActionListener));  // TODO: replace null with ActionListener to delete current Registrant
    eastBtnPanel.add(loadBtn("       Add Property       ", 's', false, addPropertyActionListener));  // TODO: replace null with ActionListener to add Property to current registrant
    eastBtnPanel.add(loadBtn("  Delete Current Property ", 's', false, deletePropertyActionListener));  // TODO: replace null with ActionListener to delete currently displayed property
    eastBtnPanel.add(loadBtn("Change Registration Number", 's', false, changeRegNumActionListener));  // TODO: replace null with ActionListener that prompts the user with a dialog of registerd regNums,
    return eastBtnPanel;                                                        //       and uses this value in to replace the regNum assigned to each of the currently displayed properties
  }

  private static JPanel loadPropertyPanel(ArrayList<Property> props) {
    propertiesPanel.setPreferredSize(getStandardDimension(10, 140));
    JScrollPane propertyScrollPanel = new JScrollPane(getPropertiesScrollPanel(props)) ;
    propScrollPane = propertyScrollPanel;
    propertyScrollPanel.setPreferredSize(getStandardDimension(5,120));
    propertiesPanel.add(propertyScrollPanel);
    return propertiesPanel;
  }

  private static JPanel loadSouthBtnPanel() {
    southBtnPanel.setLayout(new GridLayout(1, 2, SPACE, SPACE));
    southBtnPanel.add(loadBtn("Load Land Registry", 'r', true, loadLandRegistryActionListener));    // TODO: replace null with ActionListener to load ArrayLists from Backup files
    southBtnPanel.add(loadBtn("Backup Land Registry", 'b', false, backupLandRegistryActionListener)); // TODO: replace null with ActionListener to backup ArrayLists to files
    southBtnPanel.setAlignmentX(RIGHT_ALIGNMENT);
    mainPanel.add(loadBtn("       Exit        ", 'x', true, (ActionEvent e)->{
      f.dispose();
      // auto save only when there is one or more registrants
      if (!getRegControl().listOfRegistrants().isEmpty()) {
        backupLandRegistry();
      }
    }), exitBtnConstraints);
    return southBtnPanel;
  }

  private static JPanel getRestrantsScrollPanel(ArrayList<Registrant> regs) {
    JPanel regsScrollPanel = new JPanel(new GridLayout(regs==null?1:regs.size(), 1));
    if (regs != null && !regs.isEmpty())
      for (Registrant reg: regs) {
        JPanel thisRegsNames = new JPanel(new GridLayout(3,1));
        thisRegsNames.setPreferredSize(getStandardDimension(-20,120));
        thisRegsNames.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        thisRegsNames.add(displayInformation("Registrant's #: ", Integer.toString(reg.getRegNum())));
        thisRegsNames.add(displayInformation("First Name:     ", reg.getFirstName()));
        thisRegsNames.add(displayInformation("Last Name:      ", reg.getLastName()));
        regsScrollPanel.add(thisRegsNames);
      }
    return regsScrollPanel;
  }

  private static JPanel getPropertiesScrollPanel(ArrayList<Property> props) {
    JPanel propsScrollPane = new JPanel(new GridLayout(props==null?1:props.size(), 1));
    propsScrollPane.setPreferredSize(getStandardDimension(-20, 150 * (props == null ? 1 : props.size())));

    if (props != null && !props.isEmpty())
      for (Property prop: props) {
        JPanel thisPropertyInfo = new JPanel(new GridLayout(3,2));
        thisPropertyInfo.setPreferredSize(getStandardDimension(-20,240));
        thisPropertyInfo.setBorder(BorderFactory.createLineBorder(Color.black));
        thisPropertyInfo.add(displayInformation("Left  ", Integer.toString(prop.getXLeft())));
        thisPropertyInfo.add(displayInformation("Top   ", Integer.toString(prop.getYTop())));
        thisPropertyInfo.add(displayInformation("Length", Integer.toString(prop.getXLength())));
        thisPropertyInfo.add(displayInformation("Width ", Integer.toString(prop.getYWidth())));
        thisPropertyInfo.add(displayInformation("Area  ", Integer.toString(prop.getArea())));
        thisPropertyInfo.add(displayInformation("Taxes ", Double.toString(prop.getTaxes())));
        propsScrollPane.add(thisPropertyInfo);
      }
    return propsScrollPane;
  }

  // Utility to set a button in a JPanel ON/OFF.  Use constants above for component number, and ON/OFF values for boolean parameter
  private static boolean setBtnIn(JPanel p, int componentNumber, boolean onOff) {
    if (p.getComponentCount() < componentNumber) return false;
    JButton b = (JButton)p.getComponent(componentNumber - 1);
    b.setEnabled(onOff);
    return true;
  }

  // Utility to set a group of buttons in a JPanel ON/OFF.  Use ON/OFF for boolean parameter, and set the value of exceptFor with the buttons to be excluded
  private static void turnAllBtnsIn(JPanel p, boolean b, Integer... exceptFor) {
    List<Integer> arInt = Arrays.asList(exceptFor);
    for (int n = 1; n <= p.getComponentCount(); n++ ) {
      if (!(arInt.contains(n)))
        setBtnIn(p, n, b);
    }
  }

  // Gets standard dimension information and resizes its length and width by deltaX and deltaY
  private static Dimension getStandardDimension(int deltaX, int deltaY) {
    Dimension d = (Dimension)defaultDimension.clone();
    d.setSize(d.getWidth() + deltaX, d.getHeight() + deltaY);
    return d;
  }

  private static JPanel displayInformation(String label, String name) {
    JLabel l = new JLabel(label);
    l.setFont(defaultFont);

    JTextField t = new JTextField();
    t.setFont(defaultFont);   t.setPreferredSize(getStandardDimension(-360,0));
    t.setEditable(false);     t.setEnabled(false);
    t.setText(name);

    JPanel p = new JPanel();
    p.add(l); p.add(t);
    return p;
  }

  private static JButton loadBtn(String label, char keyboardShortcut, boolean onOff, ActionListener act) {
    JButton btn = new JButton(label);
    btn.addActionListener(act);
    btn.setFont(defaultFont);
    btn.setEnabled(onOff);
    btn.setMnemonic(keyboardShortcut);
    return btn;
  }

  // ArrayList of Registrants for test purposes;
  // Call mainPanel.add(loadRegistrantsPanel(getRegTestArrayList), registrantConstraints);
  // on line 80 to load these registrants into the Registrants combo box
  // TODO: Delete this code before uploading your submission
  private static ArrayList<Registrant> getRegTestArrayList(){
    ArrayList<Registrant> regs = new ArrayList<>();
    regs.add(new Registrant("Bob Smith"));
    regs.add(new Registrant("Yitzak Rosenblum"));
    regs.add(new Registrant("Shivan Sharma"));
    return regs;
  }

  // ArrayList of Properties for test purposes;
  // Call mainPanel.add(loadPropertyPanel(getPropertyTestArrayList()), propertyConstraints);
  // on line 80 to load these properties into the Properties combo box
  // TODO: Delete this code before uploading your submission
  private static ArrayList<Property> getPropertyTestArrayList(){
    ArrayList<Property> props = new ArrayList<>();
    props.add(new Property(100, 200, 400, 300, 1000));
    props.add(new Property(100, 200, 800, 200, 1002));
    props.add(new Property(20, 40, 300, 150, 1001));
    props.add(new Property(20, 50, 400, 0, 1001));
    props.add(new Property(100, 100, 20, 800, 1001));
    return props;
  }

  // method displays a warning dialog based on input message and header
  private static void displayWarningDialog (String message, String header) {
    JFrame exceptionFrame = new JFrame();
    JOptionPane.showMessageDialog(exceptionFrame, message, header, JOptionPane.WARNING_MESSAGE);
  }

  // this method add a listener to the ComboBox to get event when change
  private static void addListenerToComboBox() {
    regNumBox.addItemListener (new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent itemEvent) {
        updateUI();
      }
    });
  }

  // This method will return the index of the regNum in the list of Registrants
  private static int findComponentIndex(int regNum) {
    ArrayList<Registrant> regList = getRegControl().listOfRegistrants();
    for (int i = 0; i < regList.size(); i++) {
      int current = regList.get(i).getRegNum();
      if (regNum == current) {
        return i + 1;
      }
    }
    return 0;
  }

  // This method will change the state of the regScrollPane to update UI after any task
  private static void setRegistrantScrollPane(ArrayList<Registrant> listOfReg) {
    registrantsPanel.remove(regScrollPane);
    registrantsPanel.add(getRestrantsScrollPanel(listOfReg));
  }

  // This method will update the UI based on the situations of some important component
  private static void updateUI() {
    // remove all registrants and properties panels
    registrantsPanel.removeAll();
    propertiesPanel.removeAll();
    // declare new ArrayList of Registrant and Property
    ArrayList<Registrant> listOfRegistrant;
    ArrayList<Property> listOfProperties;
    int choice = getCurrentRegNumChoice(); // The current choice of the ComboBox
    loadRegistrantsPanel(getRegControl().listOfRegistrants()); // Reload the registrantsPanel
    turnAllBtnsIn(southBtnPanel, ON); // Turns all south buttons to ON before doing something
    if (getRegControl().listOfAllProperties().size() > 0) {
      // if there is one or more properties, there must be one or more registrant,
      // so turn all the East buttons ON
      turnAllBtnsIn(eastBtnPanel, ON);
    } else if (getRegControl().listOfRegistrants().size() > 0) {
      // if there is no property but one or more registrant, turn on only DELETE_REGISTRANT and
      // ADD_PROPERTY
      turnAllBtnsIn(eastBtnPanel, ON, DELETE_CURRENT_PROPERTY, CHANGE_REGISTRATION_NUMBER);
    } else {
      // if there are no registrant, turn off all exept RELOAD_LAND_REGISTRY and EXIT_ON_CLOSE
      turnAllBtnsIn(eastBtnPanel, OFF, ADD_NEW_REGISTRANT);
      turnAllBtnsIn(southBtnPanel, OFF, EXIT_ON_CLOSE, RELOAD_LAND_REGISTRY);
    }

    // turn on RELOAD_LAND_REGISTRY only when the files exist
    if (!(new File(REGISTRANTS_FILE).exists() && new File(PROPERTIES_FILE).exists())) {
      turnAllBtnsIn(southBtnPanel, OFF, EXIT_ON_CLOSE);
    };

    if (choice <= 0) {
      // if index of choice <= 0 (no registrants or display all registrant), listOfProperties
      // equals to all Properties, and turn all the East button off except ADD_NEW_REGISTRANT
      listOfProperties = getRegControl().listOfAllProperties();
      turnAllBtnsIn(eastBtnPanel, false, ADD_NEW_REGISTRANT);
    } else {
      // Set listOfRegistrant and listOfProperties only contain items has the current choice of
      // regNum from the ComboBox
      listOfRegistrant = new ArrayList<>();
      listOfRegistrant.add(getRegControl().findRegistrant(choice));
      listOfProperties = getRegControl().listOfProperties(choice);
      setRegistrantScrollPane(listOfRegistrant);
    }
    // reload the scroll panels
    loadPropertyPanel(listOfProperties);
    // keep the current selected index
    if (choice > 0) {
      regNumBox.setSelectedIndex(findComponentIndex(choice));
    }
    // reload the frame from: https://docs.oracle.com/javase/7/docs/api/javax/swing/JComponent.html#revalidate()
    f.revalidate();
    addListenerToComboBox();
  }

  // get the regNum from the choice index of the ComboBox
  private static int getCurrentRegNumChoice() {
    int index = regNumBox.getSelectedIndex();
    String item = regNumBox.getItemAt(index);
    if (index <= 0) {
      return -1;
    }
    return Integer.parseInt(item);
  }

  private static RegControl getRegControl() {
    return rc;
  }

  // the addNewRegistrant listener
  private static ActionListener addRegistrantActionListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        displayAddRegistrant();
        updateUI();
      } catch (BadLandRegistryException exception) {
        displayWarningDialog(exception.getMessage(), exception.getHeader());
      }
    }
  };

  // the method to show the panels when add new registrant
  private static void displayAddRegistrant() {
    JFrame addRegistrantFrame = new JFrame();
    String registrantName = JOptionPane.showInputDialog(addRegistrantFrame, "Enter registrant's first and last name:", "New registrant input", JOptionPane.INFORMATION_MESSAGE);
    if (RegControl.isInputNull(registrantName)) {
      throw new BadLandRegistryException("Add new registrant", "Add new registrant canceled.");
    } else if (RegControl.isInputEmpty(registrantName)) {
      throw new BadLandRegistryException("Missing value", "Missing an input value");
    }
    getRegControl().addNewRegistrant(new Registrant(registrantName));
  }

  // the deleteRegistrant listener
  private static ActionListener deleteRegistrantActionListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      displayDeleteRegistrant();
      updateUI();
    }
  };

  // the method to show the panels when add new registrant
  private static void displayDeleteRegistrant() {
    JFrame deleteRegistrantFrame = new JFrame();
    // the confirm dialog that the user will delete both registrant and properties
    int option = JOptionPane.showConfirmDialog(deleteRegistrantFrame,
        "You are about to delete a registrant number and all the its associated properties; Do you wish to continue?",
        "Delete Registrant Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    // if YES, it delete both registrant and properties
    if (option == JOptionPane.YES_OPTION) {
      int choice = getCurrentRegNumChoice();
      getRegControl().deleteRegistrant(choice);
      regNumBox.setSelectedIndex(0);
      JFrame deleteRegistrantConfirmFrame = new JFrame();
      JOptionPane.showMessageDialog(deleteRegistrantConfirmFrame, "The current registrant and related properties deleted.",
          "Delete Registrant Finished", JOptionPane.INFORMATION_MESSAGE);
    } else {
      // else, show a message that the user canceled
      JFrame deleteRegistrantConfirmFrame = new JFrame();
      JOptionPane.showMessageDialog(deleteRegistrantConfirmFrame, "Deleting registrant task is canceled!",
          "Delete Registrant Canceled", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  // the addNewProperty listener
  private static ActionListener addPropertyActionListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      try {
        int choice = getCurrentRegNumChoice();
        displayAddProperty(choice);
        regNumBox.setSelectedIndex(findComponentIndex(choice));
        updateUI();
      } catch (BadLandRegistryException exception) {
        displayWarningDialog(exception.getMessage(), exception.getHeader());
      }
    }
  };

  // method that shows the panels to create and add new property
  private static void displayAddProperty(int regNum) {
    // Add JTextField from https://stackoverflow.com/questions/41904362/multiple-joptionpane-input-dialogs
    JPanel addPropPanel = new JPanel();
    addPropPanel.setLayout(new GridLayout(4, 2, 2, 2));
    // TextField for 4 position and size variables
    JTextField xLeft = new JTextField();
    JTextField yTop = new JTextField();
    JTextField xLength = new JTextField();
    JTextField yWidth = new JTextField();

    // all all four TextFields to the addPropPanel
    addPropPanel.add(new JLabel("Enter left position (xLeft): "));
    addPropPanel.add(xLeft);
    addPropPanel.add(new JLabel("Enter top position (yTop): "));
    addPropPanel.add(yTop);
    addPropPanel.add(new JLabel("Enter length (xLength): "));
    addPropPanel.add(xLength);
    addPropPanel.add(new JLabel("Enter width (yWidth): "));
    addPropPanel.add(yWidth);

    JOptionPane.showMessageDialog(f, addPropPanel, "Add Property input", JOptionPane.INFORMATION_MESSAGE);
    // throw exception if the input values are invalid (not contains only number)
    if (!RegControl.doesStringContainOnlyNumber(xLength.getText()) || !RegControl.doesStringContainOnlyNumber(yWidth.getText()) ||
        !RegControl.doesStringContainOnlyNumber(xLeft.getText()) || !RegControl.doesStringContainOnlyNumber(yTop.getText())) {
      throw new BadLandRegistryException("Invalid Registration number",
          "Position and size values must contain digits only; alphabetic and special characters are prohibited");
    } else if (RegControl.isInputEmpty(xLength.getText()) || RegControl.isInputEmpty(yWidth.getText()) ||
        RegControl.isInputEmpty(xLeft.getText()) || RegControl.isInputEmpty(yTop.getText())) {
      // throws missing value exception if one of four inputs is empty
      throw new BadLandRegistryException("Missing value", "Missing an input value");
    } else if (RegControl.doesPropertyExceedSize(xLeft.getText(), yTop.getText(), xLength.getText(), yWidth.getText())) {
      // throws exception if the input values exceed size 1000
      throw new BadLandRegistryException("Property exceeds available size",
          "The property requested extends beyond the boundary of the available land");
    } else if (RegControl.isPropertyBelowMinimumSize(xLength.getText(), yWidth.getText())) {
      // throws exception if the input values below the minimum sizes
      throw new BadLandRegistryException("Property below minimum size",
          "The minimum property size entered must have a length of at least 20 m and a width of 10 m");
    }
    // create a new prop
    Property newProp = new Property(Integer.parseInt(xLength.getText()), Integer.parseInt(yWidth.getText()),
        Integer.parseInt(xLeft.getText()), Integer.parseInt(yTop.getText()), regNum);
    // check if the new property overlaps another, if yes, throws property overlap exception, else, add it to the list
    Property overlapProp = getRegControl().propertyOverlaps(newProp);
    if (overlapProp != null) {
      throw new BadLandRegistryException("Property overlap",
          "The property entered overlaps with an existing property with coordinates " +
              overlapProp.getXLeft() + " " + overlapProp.getYTop() + " and size " +
              overlapProp.getXLength() + ", " + overlapProp.getYWidth());
    }
    getRegControl().addNewProperty(newProp);
  }

  // the deleteProperty listener
  private static ActionListener deletePropertyActionListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      displayDeleteProperty();
      updateUI();
    }
  };

  // method that shows the panels to delete property
  private static void displayDeleteProperty() {
    JFrame deletePropFrame = new JFrame();
    // ask if the user really wants to delete
    int option = JOptionPane.showConfirmDialog(deletePropFrame, "Do you wish to delete all the properties with the current registration number?",
        "Delete Property Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    if (option == JOptionPane.YES_OPTION) {
      // delete the property with the regNum choice at the ComboBox
      int choice = getCurrentRegNumChoice();
      getRegControl().deleteProperties(getRegControl().listOfProperties(choice));
      regNumBox.setSelectedIndex(0); //set the current value of ComboBox back to 0
      // the confirmation frame
      JFrame deletePropConfirmFrame = new JFrame();
      JOptionPane.showMessageDialog(deletePropConfirmFrame, "Properties with the current registration number are deleted!",
          "Delete Property Finished", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  // the changePropertyRegNum listener
  private static ActionListener changeRegNumActionListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      displayChangeRegNum();
      updateUI();
    }
  };

  // method that shows the panels to change properties' regNum
  private static void displayChangeRegNum() {
    int choice = getCurrentRegNumChoice();
    JFrame newRegNumFrame = new JFrame();
    // From https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
    // get list of current regNum and store in the below ArrayList
    ArrayList<Registrant> listOfRegistrant = getRegControl().listOfRegistrants();
    Object[] allCurrentRegNums = new Object[listOfRegistrant.size()];
    for (int i = 0; i < listOfRegistrant.size(); i++) {
      allCurrentRegNums[i] = listOfRegistrant.get(i).getRegNum();
    }
    // show a dialog and get the user's choice of newRegNum as Object type to check if it is null or not
    Object newRegNumChoice = JOptionPane.showInputDialog(newRegNumFrame, "Select one of current registrant numbers",
        "Registrant's numbers", JOptionPane.INFORMATION_MESSAGE, null, allCurrentRegNums, allCurrentRegNums[0]);
    // if the above object is null (canceled choice), do nothing, else call changePropertyRegistrant method from RegControl to change
    if (newRegNumChoice != null) {
      getRegControl().changePropertyRegistrant(getRegControl().listOfProperties(choice), (int) newRegNumChoice);
      JFrame changePropConfirmFrame = new JFrame();
      // confirmation frame
      JOptionPane.showMessageDialog(changePropConfirmFrame, String.format("Operation completed; the new registration number, %d, has replaced %d in all affected properties.", (int) newRegNumChoice, choice),
          "Change Properties' Registration Number Completed", JOptionPane.INFORMATION_MESSAGE);
      regNumBox.setSelectedIndex(0);
    }
  }

  // the loadLandRegistryFromBackup listener
  private static ActionListener loadLandRegistryActionListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      getRegControl().refreshProperties();
      getRegControl().refreshRegistrants();
      updateUI();
    }
  };

  // call saveToFile() method from RegControl twice to save the two files
  private static void backupLandRegistry() {
    getRegControl().saveToFile(getRegControl().listOfRegistrants(), REGISTRANTS_FILE);
    getRegControl().saveToFile(getRegControl().listOfAllProperties(), PROPERTIES_FILE);
  }

  // the loadLandRegistryFromBackup listener
  private static ActionListener backupLandRegistryActionListener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      // call backupLandRegistry() method above to save to file
      backupLandRegistry();
      // show the confirmation panel
      JFrame confirmBackupPane = new JFrame();
      JOptionPane.showMessageDialog(confirmBackupPane, "Land Registry has been backed up to file.",
          "Backup Land Registry Finished", JOptionPane.INFORMATION_MESSAGE);
    }
  };
}

