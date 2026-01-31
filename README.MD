# Guitar Pitch Detection Engine

A low-latency, real-time pitch detection engine designed specifically for guitar input.

This engine focuses on fast response and musical stability rather than theoretical accuracy.
It is intended for monophonic instruments and real-time use.

## Scope

- Single-note (monophonic) pitch detection
- Real-time processing
- Guitar-optimized frequency handling

Out of scope:
- Chords or polyphonic detection
- Tuning modes
- Music theory helpers
- UI or platform-specific presentation

## Architecture

Microphone input  
→ Audio capture (44.1 kHz, mono)  
→ RMS noise gate  
→ Pitch detection (YIN)  
→ Median smoothing  
→ Note stability window  
→ Guitar-specific bias  
→ Note output  

## Technical Details

- Sample rate: 44.1 kHz
- Channel: mono
- Window size: 2048 samples
- Hop size: 512 samples
- Pitch algorithm: YIN
- Stability window: ~55–80 ms
- Guitar frequency guard: ~70–1400 Hz

## Guitar-Specific Logic

To improve usability with real guitars, the engine applies:

- Frequency range limiting to ignore non-guitar signals
- Harmonic bias to prefer fundamental frequencies
- Guard against rapid octave jumps
- Non-blocking correction to preserve responsiveness

These adjustments are designed to improve musical feel without adding latency.

## Performance

- Designed for low perceived latency
- Typical end-to-end response: ~90–120 ms
- No dynamic allocations in the audio callback
- Processing performed entirely on a native audio thread

## Usage Notes

- Intended for real audio input only
- Emulators or prerecorded audio may produce unreliable results
- Best tested with a real guitar and device microphone

## Privacy

This engine does not record, store, or transmit audio.
All processing is performed locally in memory.

## License

MIT
