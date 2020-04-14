import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.midi.*;

public class BeatBox {

    JPanel mainPanel;
    ArrayList<JCheckBox> checkboxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame theFrame;
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};
    String[] instrumentNames = {
        "Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
        "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell",
        "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"
    };

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;

        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);            
        } catch(InvalidMidiDataException ex) {
            ex.printStackTrace();
        }

        return event;
    }

    public void makeTracks(int[] list) {
        for (int i = 0; i < 16; i++) {
            int key = list[i];

            if (key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            }
        }
    }

    public void buildTrackAndStart() {
        int[] trackList = null;

        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; i++) {
            int key = instruments[i];
            trackList = new int[16];

            for (int j = 0; j < 16; j++) {
                JCheckBox jc = checkboxList.get(j + (16 * i));

                if (jc.isSelected()) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            }

            makeTracks(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        }

        track.add(makeEvent(192, 9, 1, 0, 15));

        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.setTempoInBPM(120);
            sequencer.start();            
        } catch(InvalidMidiDataException ex) {
            ex.printStackTrace();
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
                JCheckBox check = (JCheckBox) checkboxList.get(i);

                if (check.isSelected()) {
                    checkboxState[i] = true;
                }
            }

            try {
                JFileChooser fileSave = new JFileChooser();
                fileSave.showSaveDialog(theFrame);
                FileOutputStream fileStream = new FileOutputStream(fileSave.getSelectedFile());
                ObjectOutputStream os = new ObjectOutputStream(fileStream);
                os.writeObject(checkboxState);
                os.close();

            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class MyReadInListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            boolean[] checkboxState = null;

            try {
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.showOpenDialog(theFrame);
                FileInputStream fileIn = new FileInputStream(fileOpen.getSelectedFile());
                ObjectInputStream is = new ObjectInputStream(fileIn);
                checkboxState = (boolean[]) is.readObject();
                is.close();

            } catch(IOException ex) {
                ex.printStackTrace();
            } catch(ClassNotFoundException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 256; i++) {
                JCheckBox check = (JCheckBox) checkboxList.get(i);

                if (checkboxState[i]) {
                    check.setSelected(true);

                } else {
                    check.setSelected(false);
                }
            }
            sequencer.stop();
            buildTrackAndStart();
        }
    }

    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();

            sequencer.setTempoInBPM(120);
            sequencer.open();           
        } catch(InvalidMidiDataException ex) {
            ex.printStackTrace();
        } catch(MidiUnavailableException e) {
            e.printStackTrace();
        }
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
        JButton save = new JButton("Save");
        JButton restore = new JButton("Restore");

        start.addActionListener(new MyStartListener());
        stop.addActionListener(new MyStopListener());
        upTempo.addActionListener(new MyUpTempoListener());
        downTempo.addActionListener(new MyDownTempoListener());
        save.addActionListener(new MySendListener());
        restore.addActionListener(new MyReadInListener());
        buttonBox.add(start);
        buttonBox.add(stop);
        buttonBox.add(upTempo);
        buttonBox.add(downTempo);
        buttonBox.add(save);
        buttonBox.add(restore);

        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }

        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();

            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }

        setUpMidi();

        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);
        background.add(BorderLayout.CENTER, mainPanel);
        grid.setVgap(1);
        grid.setHgap(2);
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theFrame.getContentPane().add(background);
        theFrame.setBounds(50, 50, 300, 300); 
        theFrame.pack();
        theFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new BeatBox().buildGUI();
    }

}