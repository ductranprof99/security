# security
# instruction : PC1 PC2 is permutation for key, IP is permutation for data, 
----------------------
# key create:
 use PC1 to permutate the key (56 bit)(binary)<br>
 then divide the output to 2 part (2 part 28 bit = 56 bit)<br>
 shift bit, each shift create  CxDx couple (this two step use in single shift bit method)<br>
 use CxDx to create Kx with permutation function and P2<br>
-----------------
# encode data:
E permutation for R 32 bit is for exspansion the 32 bit permutated data into 48 bit (R0_32 -> R0_48 )<br>
after x pansion, xor the expansioned part with the K (must be same order)<br>
got the 48 bit after xor, shrink it with S_TABLE : <br>
shrink: 48 = 6bit * 8 group; each 6bit = axxxxa ( with aa is row, xxxx is column; count as binary)<br>
after shrink, permutated it last time with LAST_P; got the <br>
----------------

#waiting for implement finalpart
P(B) = 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0<br>
Rn_1 = [1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1]<br>

ketqua cipher text
L1 R1  * IP ^-1= [0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1] 
