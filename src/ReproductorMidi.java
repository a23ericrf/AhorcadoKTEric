import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.Synthesizer;

class ReproductorMidi {

    Sequencer sequencer;
    Synthesizer synthesizer;

    public ReproductorMidi(String ficheroMidi) throws Exception {
        // simplificamos el throws con Exception para hacer código limpio (aunque de peor calidad)
        sequencer = MidiSystem.getSequencer();
        sequencer.open();

        // Verificamos si tenemos un sintetizador para controlar el volumen
        if (sequencer instanceof Synthesizer) {
            synthesizer = (Synthesizer) sequencer;
        } else {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
        }

        InputStream is = new BufferedInputStream(new FileInputStream(new File(ficheroMidi)));
        sequencer.setSequence(is);
        sequencer.start();
        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
    }

   

    void cerrar() {
        if (synthesizer != null) {
            synthesizer.close();
        }
        sequencer.close();
    }
}
