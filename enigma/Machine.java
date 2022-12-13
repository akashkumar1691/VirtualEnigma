package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author
 */
public class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls. ALLROTORS contains all the
     *  available rotors. */
    public Machine(Alphabet alpha, int numRotors, int pawls,
            Rotor[] allRotors) {
        this.alphabet = alpha;
        this.numRotors = numRotors;
        this.pawls = pawls;
        this.allRotors = allRotors;
        this.myRotors = new Rotor[numRotors];
    }

    /** Return the number of rotor slots I have. */
    public int numRotors() {
        return numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    public int numPawls() {
        return pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    public void insertRotors(String[] rotors) {
        for(int i=0; i< rotors.length; i++){
            rotors[i] = rotors[i].toUpperCase();
            for(int h = 0; h < this.allRotors.length; h++){
                if (rotors[i].equals(this.allRotors[h].name().toUpperCase())){
                    this.myRotors[i] = this.allRotors[h];
                    break;
                }
            }
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 upper-case letters. The first letter refers to the
     *  leftmost rotor setting (not counting the reflector).  */
    public void setRotors(String setting) {
        int len = this.myRotors.length-1;
        for (int i =1; i<=len;i++){
            this.myRotors[i].set(setting.charAt(i-1));
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    public void setPlugboard(Permutation plugboard) {

        this.plugboard = new FixedRotor("plugboard", plugboard);
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    public int convert(int c) {
    	// HINT: This one is tough! Consider using a helper method which advances
    	//			the appropriate Rotors. Then, send the signal into the
    	//			Plugboard, through the Rotors, bouncing off the Reflector,
    	//			back through the Rotors, then out of the Plugboard again.
        this.advance();
        c = this.plugboard.convertForward(c);
        int len = this.myRotors.length -1;
        for (int i= len; i>0; i-- ){
              c = this.myRotors[i].convertForward(c);
        }
        c = this.myRotors[0].convertForward(c);
        for (int i=1; i<=len; i++ ){
            c = this.myRotors[i].convertBackward(c);
        }
        c = this.plugboard.convertBackward(c);
        return c;
    }

    /** Optional helper method for convert() which rotates the necessary Rotors. */
    private void advance() {
    	Boolean[] doweturn = new Boolean[this.myRotors.length];
    	int len = this.myRotors.length -1;
    	for(int i =1; i <= len; i++){
    	    if (this.myRotors[i].atNotch()){
    	        doweturn[i] =true;
    	        doweturn[i-1] = true;
         }
            else{
                doweturn[i]=false;
            }
        }
    	doweturn[len] = true;
    	doweturn[0] = false;
    	for(int i =0; i<=len; i++){
    	    if (doweturn[i] && this.myRotors[i].rotates() && len-i < this.numPawls()){
    	        this.myRotors[i].advance();
            }
        }
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    public String convert(String msg) {
        msg = msg.toUpperCase();
        msg = msg.trim();
        msg = msg.replaceAll("\\s+", "");
        for (int i = 0; i < msg.length(); i++){
            for(int h =0; h<msg.length(); h++) {
            if (!(this.alphabet.contains(msg.charAt(i)))){
                    msg = msg.replaceAll(Character.toString(msg.charAt(i)), "");
                }}
        }
        String newmsg = "";
        for (int i = 0; i <= msg.length()-1; i++){
            char c;
            c = this.alphabet.toChar(this.convert(this.alphabet.toInt( msg.charAt(i))));
            newmsg += c;
        }
        return newmsg;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet alphabet;
    private int numRotors;
    private int pawls;
    private Rotor[] allRotors;
    private Rotor[] myRotors;
    private FixedRotor plugboard;

    // To run this through command line, from the proj0 directory, run the following:
    // javac enigma/Machine.java enigma/Rotor.java enigma/FixedRotor.java enigma/Reflector.java enigma/MovingRotor.java enigma/Permutation.java enigma/Alphabet.java enigma/CharacterRange.java enigma/EnigmaException.java
    // java enigma/Machine
    public static void main(String[] args) {
        CharacterRange upper = new CharacterRange('A', 'Z');
        MovingRotor rotorI = new MovingRotor("I",
                new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", upper),
                "Q");
        MovingRotor rotorII = new MovingRotor("II",
                new Permutation("(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)", upper),
                "E");
        MovingRotor rotorIII = new MovingRotor("III",
                new Permutation("(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", upper),
                "V");
        MovingRotor rotorIV = new MovingRotor("IV",
                new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)", upper),
                "J");
        MovingRotor rotorV = new MovingRotor("V",
                new Permutation("(AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)", upper),
                "Z");
        FixedRotor rotorBeta = new FixedRotor("Beta",
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", upper));
        FixedRotor rotorGamma = new FixedRotor("Gamma",
                new Permutation("(AFNIRLBSQWVXGUZDKMTPCOYJHE)", upper));
        Reflector rotorB = new Reflector("B",
                new Permutation("(AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", upper));
        Reflector rotorC = new Reflector("C",
                new Permutation("(AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) (LM) (PW) (QZ) (SX) (UY)", upper));

        Rotor[] allRotors = new Rotor[9];
        allRotors[0] = rotorI;
        allRotors[1] = rotorII;
        allRotors[2] = rotorIII;
        allRotors[3] = rotorIV;
        allRotors[4] = rotorV;
        allRotors[5] = rotorBeta;
        allRotors[6] = rotorGamma;
        allRotors[7] = rotorB;
        allRotors[8] = rotorC;

        Machine machine = new Machine(upper, 5, 3, allRotors);
        machine.insertRotors(new String[]{"B", "BETA", "III", "IV", "I"});
        machine.setRotors("AXLE");
        machine.setPlugboard(new Permutation("(HQ) (EX) (IP) (TR) (BY)", upper));

        System.out.println(machine.numRotors() == 5);
        System.out.println(machine.numPawls() == 3);
        System.out.println(machine.convert(5) == 16);
        System.out.println(machine.convert(17) == 21);
        System.out.println(machine.convert("OMHISSHOULDERHIAWATHA").equals("PQSOKOILPUBKJZPISFXDW"));
        System.out.println(machine.convert("TOOK THE CAMERA OF ROSEWOOD").equals("BHCNSCXNUOAATZXSRCFYDGU"));
        System.out.println(machine.convert("Made of sliding folding rosewood").equals("FLPNXGXIXTYJUJRCAUGEUNCFMKUF"));
    }
}
