# VirtualEnigma
a virtual simulation of the German Enigma cipher device used in WWII

As per the [Enigma Machine Wikipedia page](https://en.wikipedia.org/wiki/Enigma_machine): 
<blockquote> The Enigma machine is a cipher device developed and used in the early- to mid-20th century to protect commercial, diplomatic, and military communication. It was employed extensively by Nazi Germany during World War II, in all branches of the German military. The Enigma machine was considered so secure that it was used to encipher the most top-secret messages.[1]

The Enigma has an electromechanical rotor mechanism that scrambles the 26 letters of the alphabet. In typical use, one person enters text on the Enigma's keyboard and another person writes down which of the 26 lights above the keyboard illuminated at each key press. If plain text is entered, the illuminated letters are the ciphertext. Entering ciphertext transforms it back into readable plaintext. The rotor mechanism changes the electrical connections between the keys and the lights with each keypress.

The security of the system depends on machine settings that were generally changed daily, based on secret key lists distributed in advance, and on other settings that were changed for each message. The receiving station would have to know and use the exact settings employed by the transmitting station to successfully decrypt a message.  </blockquote>

Essentially, the Enigma Machine uses a complex set of rotors, rings, ratchets, pawls, and a plugboard set to a specific configuration in order to translate plaintext to ciphertext and vice versa. In this project, a virtual version of the Enigma machine is created such that the different alphabets and different types and arrangements of rotors can be used. A configuration in this case is represents one possible physical enigma machine. Once a configuration or machine is made, the virtual machine can be set to different settings just like the actual machine and can cipher and decipher any input text.

## Using the Virtual Enigma Machine
<p>To run the program, edit the configurations to run Main.java and supply the following as the program arguments:</p> <div class="language-plaintext highlighter-rouge"><div class="highlight"><pre class="highlight"><code>testing/correct/default.conf testing/correct/trivial.inp
</code></pre></div></div> <p>You can compare the output of this with the contents of <code class="language-plaintext highlighter-rouge">testing/correct/trivial.out</code> to see if your machine is outputting the correct results. To test against different files, simply replace the second argument (<code class="language-plaintext highlighter-rouge">testing/correct/trivial.inp</code>) with another input file (such as <code class="language-plaintext highlighter-rouge">testing/correct/trivial1.inp</code>).</p> <p>The configuration file contains descriptions of the machine and the available rotors. The data are in <em>free format</em>. That is, they consist of strings of non-whitespace characters separated by arbitrary whitespace (spaces, tabs, and newlines), so that indentation, spacing, and line breaks are irrelevant. Each file has the following contents:</p> <ul> <li>A string of the form \(`C_1`\)<code class="language-plaintext highlighter-rouge">-</code>\(`C_2`\) where \(`C_1`\) and \(`C_2`\) are non-blank characters other than “<code class="language-plaintext highlighter-rouge">-</code>”, “<code class="language-plaintext highlighter-rouge">(</code>”, and “<code class="language-plaintext highlighter-rouge">)</code>”, with \(`C_1\le C_2`\). This specifies that the alphabet consists of characters \(`\ge C_1`\) and \(`\le C_2`\), with lower-case letters mapped to upper-case.<br /> Unless you do the extra credit, \(`C_1`\) and \(`C_2`\) will always be upper-case letters.</li> <li>Two integer numerals, \(S &gt; P \ge 0\), where \(S\) is the number of rotor slots (including the reflector) and $P$ is the number of pawls–that is, the number of rotors that move. The moving rotors and their pawls are all to the right of any non-moving ones.</li> <li>Any number of rotor descriptors. Each has the following components (separated by whitespace): <ul> <li>A name containing any non-blank characters other than parentheses.</li> <li>One of the characters R, N, or M, indicating that the rotor is a reflector, a non-moving rotor, or a moving rotor, respectively. Non-moving rotors can only be used in positions \(2\) through \(S-P\) and moving rotors in positions \(S-P+1\) through \(S\).</li> <li>Immediately after the M for a moving rotor come(s) the letter(s) at which there is a notch on the rotor’s ring (no space between M and these letters).</li> <li>The cycles of the permutation, using the notation discussed above.</li> </ul> </li> </ul> <p>For example, the German Naval Enigma machine might be described with this configuration file (see Figure 1):</p> <div class="language-plaintext highlighter-rouge"><div class="highlight"><pre class="highlight"><code>          A-Z
          5 3
          I MQ      (AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)
          II ME     (FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)
          III MV    (ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)
          IV MJ     (AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)
          V MZ      (AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)
          VI MZM    (AJQDVLEOZWIYTS) (CGMNHFUX) (BPRK)
          VII MZM   (ANOUPFRIMBZTLWKSVEGCJYDHXQ)
          VIII MZM  (AFLSETWUNDHOZVICQ) (BKJ) (GXY) (MPR)
          Beta N    (ALBEVFCYODJWUGNMQTZSKPR) (HIX)
          Gamma N   (AFNIRLBSQWVXGUZDKMTPCOYJHE)
          B R       (AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) (MP)
                    (RX) (SZ) (TV)
          C R       (AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) (LM) (PW)
                    (QZ) (SX) (UY)
</code></pre></div></div> <p>The input file to your program will consist of a sequence of messages to decode, each preceded by a line giving the initial settings. Given the configuration file above, a settings line looks like this: * B BETA III IV I AXLE (YF) (ZH) (all upper case.) This particular example means that the rotors used are reflector B, and rotors Beta, III, IV, and I, with rotor I in the rightmost, or fast, slot. The remaining parenthesized items indicate that the letter pair Y and F and the pair Z and M are steckered (swapped going in from the keyboard and going out to the lights).</p> <p>In general for this particular configuration, rotor 1 is always the reflector; rotor 2 is Beta or Gamma, and each of 3-5 is one of rotors I-VIII. A rotor may not be repeated. The four letters of the following word (AXLE in the example) give the initial positions of rotors 2-5, respectively (i.e., not including the reflector). Any number of steckered pairs may follow (including none).</p> <p>After each settings line comes a message on any number of lines. Each line of a message consists only of letters, spaces, and tabs (0 or more). The program should ignore the blanks and tabs and convert all letters to upper case. The end of message is indicated either by the end of the input or by a new configuration line (distinguished by its leading asterisk). The machine is not reset between lines, but continues stepping from where it left off on the previous message line. Because the Enigma is a reciprocal cipher, a given translation may either be a decryption or encryption; you don’t have to worry about which, since the process is the same in any case.</p> <p>Output the translation for each message line in groups of five upper-case letters, separated by a space (the last group may have fewer characters, depending on the message length). Figure 2 contains an example that shows an encryption followed by a decryption of the encrypted message. Since we have yet to cover the details of File I/O, you will be provided the File IO machinery for this project.</p><pre>
             <b>Input</b>                              |         <b>Output</b>
* B BETA III IV I AXLE (HQ) (EX) (IP) (TR) (BY) | QVPQS OKOIL PUBKJ ZPISF XDW
FROM his shoulder Hiawatha                      | BHCNS CXNUO AATZX SRCFY DGU
Took the camera of rosewood                     | FLPNX GXIXT YJUJR CAUGE UNCFM KUF
Made of sliding folding rosewood                | WJFGK CIIRG XODJG VCGPQ OH
Neatly put it all together                      | ALWEB UHTZM OXIIV XUEFP RPR
In its case it lay compactly                    | KCGVP FPYKI KITLB URVGT SFU
Folded into nearly nothing                      | SMBNK FRIIM PDOFJ VTTUG RZM
But he opened out the hinges                    | UVCYL FDZPG IBXRE WXUEB ZQJO
Pushed and pulled the joints                    | YMHIP GRRE
   and hinges                                   | GOHET UXDTW LCMMW AVNVJ VH
Till it looked all squares                      | OUFAN TQACK
   and oblongs                                  | KTOZZ RDABQ NNVPO IEFQA FS
Like a complicated figure                       | VVICV UDUER EYNPF FMNBJ VGQ
in the Second Book of Euclid                    |
                                                | FROMH ISSHO ULDER HIAWA THA
* B BETA III IV I AXLE (HQ) (EX) (IP) (TR) (BY) | TOOKT HECAM ERAOF ROSEW OOD
QVPQS OKOIL PUBKJ ZPISF XDW                     | MADEO FSLID INGFO LDING ROSEW OOD
BHCNS CXNUO AATZX SRCFY DGU                     | NEATL YPUTI TALLT OGETH ER
FLPNX GXIXT YJUJR CAUGE UNCFM KUF               | INITS CASEI TLAYC OMPAC TLY
WJFGK CIIRG XODJG VCGPQ OH                      | FOLDE DINTO NEARL YNOTH ING
ALWEB UHTZM OXIIV XUEFP RPR                     | BUTHE OPENE DOUTT HEHIN GES
KCGVP FPYKI KITLB URVGT SFU                     | PUSHE DANDP ULLED THEJO INTS
SMBNK FRIIM PDOFJ VTTUG RZM                     | ANDHI NGES
UVCYL FDZPG IBXRE WXUEB ZQJO                    | TILLI TLOOK EDALL SQUAR ES
YMHIP GRRE                                      | ANDOB LONGS
GOHET UXDTW LCMMW AVNVJ VH                      | LIKEA COMPL ICATE DFIGU RE
OUFAN TQACK                                     | INTHE SECON DBOOK OFEUC LID
KTOZZ RDABQ NNVPO IEFQA FS                      |
VVICV UDUER EYNPF FMNBJ VGQ                     |
</pre><center> **Figure 2.** Sample input and output (using the Naval cipher). </center> 
