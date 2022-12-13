package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author
 */
public class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initially in its 0 setting (first character of its
     *  alphabet).
     */
    public MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        this.notches = notches;
    }

    @Override // Use this special tag when updating the behavior of a method this class inherits from Rotor
    public boolean rotates() {
        return true;
    }

    @Override // Use this special tag when updating the behavior of a method this class inherits from Rotor
    public boolean atNotch(){
        int len = this.notches.length() -1;
        int pos = this.setting();
        for(int i = 0; i<=len;i++){
            if (pos == this.permutation().alphabet().toInt(this.notches.charAt(i))){
                return true;
            }
        }
        return false;
    }

    @Override // Use this special tag when updating the behavior of a method this class inherits from Rotor
    public void advance() {

       int newpos = this.permutation().wrap(this.setting()+1);
        this.set(newpos);
    }

    private String notches;

    // To run this through command line, from the proj0 directory, run the following:
    // javac enigma/Rotor.java enigma/MovingRotor.java enigma/Permutation.java enigma/Alphabet.java enigma/CharacterRange.java enigma/EnigmaException.java
    // java enigma/MovingRotor
    public static void main(String[] args) {
        Permutation perm = new Permutation("(AB) (CDEFGHIJKLMNOPQRSTUVWXYZ)", new CharacterRange('A', 'Z'));
        MovingRotor rotor = new MovingRotor("forward one", perm,"B");

        System.out.println(rotor.name().equals("forward one"));
        System.out.println(rotor.alphabet() == perm.alphabet());
        System.out.println(rotor.permutation() == perm);
        System.out.println(rotor.rotates() == true);
        System.out.println(rotor.reflecting() == false);

        System.out.println(rotor.size() == 26);
        rotor.set(1);
        System.out.println(rotor.setting() == 1);
        System.out.println(rotor.atNotch() == true);
        rotor.set('A');
        System.out.println(rotor.setting() == 0);
        System.out.println(rotor.atNotch() == false);
        System.out.println(rotor.convertForward(0) == 1);
        System.out.println(rotor.convertBackward(1) == 0);
        rotor.advance();
        System.out.println(rotor.setting() == 1);
        System.out.println(rotor.atNotch() == true);
        System.out.println(rotor.convertForward(0) == 25);
        System.out.println(rotor.convertBackward(25) == 0);
    }

}
