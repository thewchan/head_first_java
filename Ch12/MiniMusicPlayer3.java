import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;


public class MiniMusicPlayer3 {

    static JFrame f = new JFrame("My First Music Video");
    static MyDrawPanel ml;

    class MyDrawPanel extends JPanel implements ControllerEventListener {

        boolean msg = false;

        public void controlChange(ShortMessage event) {
            msg = true;
            repaint();
        }

        public void paintComponent(Graphics g) {
            if (msg) {
                int r = (int) (Math.random() * 256);
                int gr = (int) (Math.random() * 256);
                int b = (int) (Math.random() * 256);
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

    public void setUpGui() {
        ml = new MyDrawPanel();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(ml);
        f.setBounds(30, 30, 300, 300);
        f.setVisible(true);
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

    public void go() {
        this.setUpGui();

        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();
            int r = 0;

            for (int i = 0; i < 60; i+= 4) {
                r = (int) ((Math.random() * 50) + 1);

                track.add(makeEvent(144, 1, r, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, r, 100, i + 2));
            }

            sequencer.open();
            sequencer.addControllerEventListener(ml, new int[] {127});
            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(120);
            sequencer.start();
        } catch(MidiUnavailableException ex) {
            ex.printStackTrace();
        } catch(InvalidMidiDataException exc) {
            exc.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MiniMusicPlayer3 mini = new MiniMusicPlayer3();
        mini.go();
    }
}