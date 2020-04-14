import java.awt.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.sound.midi.*;

public class BeatBoxFinal {

    JFrame theFrame;
    JPanel mainPanel;
    JList<Object> incomingList;
    JTextField userMessage;
    ArrayList<JCheckBox> checkboxList;
    int nextNum;
    Vector<String> listVector = new Vector<String>();
    String userName;
    ObjectOutputStream out;
    ObjectInputStream in;
    HashMap<String, boolean[]> otherSeqsMap = new HashMap<String, boolean[]>();
    Sequencer sequencer;
    Sequence sequence;
    Sequence mySequence = null;
    Track track;
    String[] instrumentNames = {
        "Bass Drum",
        "Closed Hi-Hat",
        "Open Hi-Hat",
        "Acoustic Snare",
        "Crash Cymbal",
        "Hand Clap",
        "High Tom",
        "Hi Bongo",
        "Maracas",
        "Whistle",
        "Low Conga",
        "Cowbell",
        "Vibraslap",
        "Low-mid Tom",
        "High Agogo",
        "Open Hi Conga"
    };
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;

        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);

        } catch(InvalidMidiDataException e) {
            e.printStackTrace();
        } 
        return event;
    }

    public void makeTracks(ArrayList<Integer> list) {
        Iterator<Integer> it = list.iterator();

        for (int i = 0; i < 16; i++) {
            Integer num = it.next();

            if (num != null) {
                int numKey = num.intValue();

                track.add(makeEvent(144, 9, numKey, 100, i));
                track.add(makeEvent(128, 9, numKey, 100, i + 1));
            }
        }
    }

    public void changeSequence(boolean[] checkboxState) {

        for (int i = 0; i < 256; i++) {
            JCheckBox check = checkboxList.get(i);

            if (checkboxState[i]) {
                check.setSelected(true);

            } else {
                check.setSelected(false);
            }
        }
    }

    public void buildTrackAndStart() {
        ArrayList<Integer> trackList = null;
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; i++) {
            trackList = new ArrayList<Integer>();

            for (int j = 0; j < 16; j++) {
                JCheckBox jc = checkboxList.get(j + (16 * i));

                if (jc.isSelected()) {
                    int key = instruments[i];
                    trackList.add(key);

                } else {
                    trackList.add(null);
                }
            }
            makeTracks(trackList);
        }
        track.add(makeEvent(192, 9, 1, 0, 15));

        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);

        } catch(InvalidMidiDataException e) {
            e.printStackTrace();
        } 
    }

    public class MyStartListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            buildTrackAndStart();
        }
    }

    public class MyStopListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            sequencer.stop();
        }
    }

    public class MyUpTempoListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 1.03));
        }
    }

    public class MyDownTempoListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 0.97));
        }
    }

    public class MySendListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            boolean[] checkboxState = new boolean[256];

            for (int i = 0; i < 256; i++) {
                JCheckBox check = checkboxList.get(i);
                String messageToSend = null;

                if (check.isSelected()) {
                    checkboxState[i] = true;
                }
            }

            try {
                out.writeObject(userName + nextNum++ + ": " + userMessage.getText());
                out.writeObject(checkboxState);

            } catch(InvalidClassException e) {
                e.printStackTrace();

            } catch(NotSerializableException ex) {
                ex.printStackTrace();

            } catch(IOException exc) {
                exc.printStackTrace();
            }
        }
    }

    public class MyListSelectionListener implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent le) {

            if (!le.getValueIsAdjusting()) {
                String selected = (String) incomingList.getSelectedValue();

                if (selected != null) {
                    boolean[] selectedState = otherSeqsMap.get(selected);

                    changeSequence(selectedState);
                    sequencer.stop();
                    buildTrackAndStart(); 
                }
            }
        }
    }

    public class MyPlayMineListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {

            if (mySequence != null) {
                sequence = mySequence;
            }
        }
    }

    public class RemoteReader implements Runnable {

        boolean[] checkboxState = null;
        String nameToShow = null;
        Object obj = null;

        public void run() {

            try {

                while ((obj = in.readObject()) != null) {
                    String nameToShow = (String) obj;
                    checkboxState = (boolean[]) in.readObject();

                    System.out.println("Retrieved object from server.");
                    System.out.println(obj.getClass());
                    otherSeqsMap.put(nameToShow, checkboxState);
                    listVector.add(nameToShow);
                    incomingList.setListData(listVector);
                }

            } catch(ClassNotFoundException e) {
                e.printStackTrace();

            } catch(InvalidClassException ex) {
                ex.printStackTrace();

            } catch(StreamCorruptedException exc) {
                exc.printStackTrace();

            } catch(OptionalDataException exce) {
                exce.printStackTrace();

            } catch(IOException excep) {
                excep.printStackTrace();
            }
        }
    }

    public void setUpMidi() {

        try {
            sequencer = MidiSystem.getSequencer();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();

            sequencer.open();
            sequencer.setTempoInBPM(120);

        } catch(MidiUnavailableException e) {
            e.printStackTrace();

        } catch(InvalidMidiDataException ex) {
            ex.printStackTrace();
        }
    }

    public void startUp(String name) {
        userName = name;

        try {
            Socket sock = new Socket("127.0.0.1", 4242);
            out = new ObjectOutputStream(sock.getOutputStream());
            in = new ObjectInputStream(sock.getInputStream());
            Thread remote = new Thread(new RemoteReader());

            remote.start();

        } catch(UnknownHostException e) {
            e.printStackTrace();

        } catch(StreamCorruptedException ex) {
            ex.printStackTrace();

        } catch(IOException exc) {
            exc.printStackTrace();
        }
        setUpMidi();
        buildGUI();
    }

    public void buildGUI() {
        theFrame = new JFrame("Cyber BeatBox");
        BorderLayout layout = new BorderLayout();
        GridLayout grid = new GridLayout(16, 16);
        JPanel background = new JPanel(layout);
        mainPanel = new JPanel(grid);
        checkboxList = new ArrayList<JCheckBox>();
        Box buttonBox = new Box(BoxLayout.Y_AXIS);
        Box nameBox = new Box(BoxLayout.Y_AXIS);
        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        JButton upTempo = new JButton("Tempo Up");
        JButton downTempo = new JButton("Tempo Down");
        JButton sendIt = new JButton("sendIt");
        userMessage = new JTextField();
        incomingList = new JList<Object>();
        JScrollPane theList = new JScrollPane(incomingList);
        
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        start.addActionListener(new MyStartListener());
        stop.addActionListener(new MyStopListener());
        upTempo.addActionListener(new MyUpTempoListener());
        downTempo.addActionListener(new MyDownTempoListener());
        sendIt.addActionListener(new MySendListener());
        buttonBox.add(start);
        buttonBox.add(stop);
        buttonBox.add(downTempo);
        buttonBox.add(upTempo);
        buttonBox.add(sendIt);
        buttonBox.add(userMessage);
        buttonBox.add(theList);
        incomingList.addListSelectionListener(new MyListSelectionListener());
        incomingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        incomingList.setListData(listVector);
        grid.setVgap(1);
        grid.setHgap(2);

        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();

            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }

        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }
        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);
        background.add(BorderLayout.CENTER, mainPanel);
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.getContentPane().add(background);
        theFrame.setBounds(50, 50, 300, 300);
        theFrame.pack();
        theFrame.setVisible(true);
    }

    public static void main(String[] args) {

        try {
            new BeatBoxFinal().startUp(args[0]);    
        } catch(ArrayIndexOutOfBoundsException e) {
            new BeatBoxFinal().startUp("User1");
        }
        
    }
}