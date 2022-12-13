package enigma;

import static enigma.EnigmaException.*;

/** Superclass that represents a rotor in the enigma machine.
 *  @author
 */
public class Rotor {

    /** A rotor named NAME whose permutation is given by PERM. */
    public Rotor(String name, Permutation perm) {
        this.name = name;
        this.permutation = perm;
        this.position = 0;
    }

    /** Return my name. */
    public String name() {
        return this.name;
    }

    /** Return my alphabet. */
    public Alphabet alphabet() {
        return this.permutation.alphabet();
    }

    /** Return my permutation. */
        public Permutation permutation() {
        return this.permutation;
    }

    /** Return the size of my alphabet. */
    public int size() {
        return this.permutation.size();
    }

    /** Return true if and only if I have a ratchet and can move. */
    public boolean rotates() {
        return false;
    }

    /** Return true if and only if I reflect. */
    public boolean reflecting() {
        return false;
    }

    /** Return my current setting.*/
    public int setting() {
        return this.position;
    }

    /** Set setting() to POSN.  */
    public void set(int posn) {
        this.position = this.permutation().wrap(posn);
    }

    /** Set setting() to character CPOSN. */
    public void set(char cposn) {
        this.set(this.permutation.alphabet().toInt(cposn));
    }

    /** Return the conversion of P (an integer in the range 0..size()-1)
     *  according to my permutation.
     *  How do we permute the index P, taking into account my current position? */
    public int convertForward(int p) {
       int l = this.permutation.permute(this.permutation.wrap(this.setting()+p));
       return this.permutation.wrap(l -this.setting());
    }

    /** Return the conversion of C (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation.  How do we invert the index E, taking into account my current position?
     *     }*/
    public int convertBackward(int c) {
        int f = this.permutation.invert(this.permutation.wrap(c+this.setting()));
        return this.permutation.wrap(f - this.setting());
    }

    /** Returns true if and only if I am positioned to allow the rotor
     * to my left to advance. */
    public boolean atNotch() {
        return false;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    public void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + this.name;
    }

    /** My name. */
    private final String name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation permutation;
    private int position;
}
