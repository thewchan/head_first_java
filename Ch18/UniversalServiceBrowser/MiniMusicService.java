import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.midi.*;

public class MiniMusicService implements Service {

    MyDrawPanel myPanel;

    class MyDrawPanel extends JPanel implements ControllerEventListener {

        boolean msg = false;

        public void controlChange(ShortMessage event) {
            msg = true;
            repaint();
        }

        public Dimension getPreferredSize() {
            return new Dimension(300, 300);
        }

        public void paintComponent(Graphics g) {

            if (msg) {
                Graphics2D g2 = (Graphics2D) g;
                int r = (int) (Math.random() * 250);
                int gr = (int) (Math.random() * 250);
                int b = (int) (Math.random() * 250);
                int ht = (int) ((Math.random() * 120) + 10);
                int width = (int) ((Math.random() * 120) + 10);
                int x = (int) ((Math.random() * 40) + 10);
                int y = (int) ((Math.random() * 40) + 10);

                g.setColor(new Color(r, gr, b));
                g.fillRect(x, y, ht, width);

                msg = false;
            }
        }
    }

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

    public class PlayItListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {

            try {
                Sequencer sequencer = MidiSystem.getSequencer();
                Sequence seq = new Sequence(Sequence.PPQ, 4);
                Track track = seq.createTrack();

                sequencer.open();
                sequencer.addControllerEventListener(myPanel, new int[] {127});

                for (int i = 0; i < 100; i += 4) {
                    int rNum = (int) ((Math.random() * 50) + 1);

                    if (rNum > 38) {
                        track.add(makeEvent(144, 1, rNum, 100, i));
                        track.add(makeEvent(176, 1, 127, 0, i));
                        track.add(makeEvent(128, 1, rNum, 100, i + 2));
                    }
                }
                sequencer.setSequence(seq);
                sequencer.start();
                sequencer.setTempoInBPM(220);

            } catch(MidiUnavailableException e) {
                e.printStackTrace();

            } catch(InvalidMidiDataException ex) {
                ex.printStackTrace();  
            }
        }
    }

    public JPanel getGuiPanel() {
        JPanel mainPanel = new JPanel();
        myPanel = new MyDrawPanel();
        JButton playItButton = new JButton("Play it");

        playItButton.addActionListener(new PlayItListener());
        mainPanel.add(myPanel);
        mainPanel.add(playItButton);
        return mainPanel;
    }
}