package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Akash Kumar & CS61B Faculty
 */
public class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */

    public Permutation(String cycles, Alphabet alphabet) {
        this.alphabet = alphabet;
        this.cycles = cycles;
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    public int size() {
        return this.alphabet.size();}


    /** Return the index result of applying this permutation to the character
     *  at index P in ALPHABET. */
    public int permute(int p) {
        p = wrap(p);
        char letter = this.alphabet.toChar(p);
        char perm = this.permute(letter);
        return this.alphabet.toInt(perm);

    }

    /** Return the index result of applying the inverse of this permutation
     *  to the character at index C in ALPHABET. */
    public int invert(int c) {
        c = wrap(c);
        char letter = this.alphabet.toChar(c);
        char perm = this.invert(letter);
        return this.alphabet.toInt(perm);
    }

    /** Return the character result of applying this permutation to the index
     * of character P in ALPHABET.*/
    public char permute(char p) {
        int index = this.cycles.indexOf(p);
        if(index == -1){
            return p;
        }
        if (this.cycles.charAt(index+1)== ')'){
            if (this.cycles.charAt(index-1)== '(' )
            {return p;}

            else{
                for(int i =index; i >= 0;i--){
                    if (this.cycles.charAt(i)== '('){
                        return this.cycles.charAt(i+1);
                    }
                }
            }
        }
        return this.cycles.charAt(index +1);


    }

    /** Return the character result of applying the inverse of this permutation
	 * to the index of character P in ALPHABET.*/
    public char invert(char c) {
        int index = this.cycles.indexOf(c);
        if(index == -1){
            return c;
        }
        if (this.cycles.charAt(index-1)== '('){
            if (this.cycles.charAt(index+1)== ')' )
            {return c;}

            else{
                for(int i =index; i <this.cycles.length();i++){
                    if (this.cycles.charAt(i)== ')'){
                        return this.cycles.charAt(i-1);
                    }
                }
            }
        }
        return this.cycles.charAt(index -1);
    }

    /** Return the alphabet used to initialize this Permutation. */
    public Alphabet alphabet() {
        return this.alphabet;
    }

    /** Alphabet of this permutation. */
    private Alphabet alphabet;
    private String cycles;
    // Some starter code for unit tests. Feel free to change these up!
    // To run this through command line, from the proj0 directory, run the following:
    // javac enigma/Permutation.java enigma/Alphabet.java enigma/CharacterRange.java enigma/EnigmaException.java
    // java enigma/Permutation
    public static void main(String[] args) {
        Permutation perm = new Permutation("(ABCDEFGHIJKLMNOPQRSTUVWXYZ)", new CharacterRange('A', 'Z'));
        System.out.println(perm.size() == 26);
        System.out.println(perm.permute('A') == 'B');
        System.out.println(perm.invert('B') == 'A');
        System.out.println(perm.permute(0) == 1);
        System.out.println(perm.invert(1) == 0);
    }
}
