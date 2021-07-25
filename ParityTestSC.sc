// A mono gutter synth example that uses an external audio input
(
Ndef(\gutterTest, {
    var sig, freq1, freq2, pitch;
    var mod, omega, damp, rate, gain, soften, gain1, gain2, q1, q2;

    mod = \mod.kr(0.2, spec:[0,10]);
    omega = \omega.kr(0.0002, spec:ControlSpec(0.0001, 1, \exponential));
    damp = \damp.kr(0.01, spec:ControlSpec(0.0001, 1, \exponential));
    rate = \rate.kr(0.03, spec:[0, 5]);
    gain = \gain.kr(1.4, spec:[0, 3.5]);
    soften = \soften.kr(1, spec:[0, 5]);
    gain1 = \gain1.kr(1.5, spec:[0.0, 2.0, \lin]);
    gain2 = \gain2.kr(1.5, spec:[0.0, 2.0, \lin]);
    q1 = \q.kr(20, spec:ControlSpec(2.5, 800, \exponential)).lag3(1);

    // freq = [56, 174, 194, 97, 139, 52, 363, 118, 353, 629];
/*    pitch = \pitchShift.kr(0.25, spec: [0.05,2.0]).lag;
    freq1 = pitch * [ 104.08913805616, 272.0241439869, 142.5394121681, 740.98235420089, 3231.1092775615, 598.48984613932, 564.11122601617, 152.53849023618, 4773.6198870775, 798.26171948236, 729.54452005837, 734.37542510625, 661.89936380362, 133.46101940276, 1715.6115033359, 11658.962024239, 6408.5610397899, 11775.302108311, 857.52846512925, 2020.251581889, 14168.220304686, 192.17654523236, 326.55730188427, 4386.8490423436];*/
    freq1 = Array.fill(24, 100);
    freq2 = Array.fill(24, 100);
    // freq2 = freq1 * Array.rand(freq1.size, 0.95,1.0);

    // q = q ! freq1.size;
/*    q1 = Array.rand(freq1.size, 0.95,1.0) * q1;
    q2 = Array.rand(freq1.size, 0.95,1.0) * q1;*/
    q1 = Array.fill(24, 0.9) * q1;
    q2 = Array.fill(24, 0.9) * q1;


    sig = GutterSynth.ar(
        gamma:         mod,
        omega:         omega,
        c:             damp,
        dt:         rate,
        singlegain: gain,
        smoothing:  soften,
        togglefilters: 1,
        distortionmethod: \distortionmethod.kr(1, spec: [0,4,\lin,1]),
        oversampling: 0,
        enableaudioinput: 0,
        audioinput: SinOsc.ar(5),
        gains1:     gain1,
        gains2:     gain2,
        freqs1:     `freq1,
        qs1:         `q1,
        freqs2:     `freq2,
        qs2:         `q2,
    );

    sig = Pan2.ar(sig, \pan.kr(0));
    sig = Limiter.ar(sig);

}).mold(2).play;
)

Ndef(\gutterTest).gui;