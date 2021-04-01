class Solution:
    def __init__(self,data,key):
        self.key_code = []
        self.R_n = None
        self.L_n = None
        self.data = data
        self.key = key  
        self.PC1 = [57,49,41,33,25,17,9,1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,6,52,44,36,63,55,47,39,31,23,15,7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4]
        self.PC2 = [14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,54,53,46,42,50,36,29,32]
        self.IP = [58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,62,54,46,38,30,22,14,6,64,56,48,40,32,24,16,8,57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7]
        self.IP_minus = [40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,38,6,46,14,54,22,62,30,37,5,45,13,53,21,61,29,36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27,34,2,42,10,50,18,58,26,33,1,41,9,49,17,57,25]
        self.E = [32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1]
        self.SHIFT_ORDER = [1,2,4,6,8,10,12,14,15,17,19,21,23,25,27,28]
        self.S1 = [[14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7],
                  [0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8],
                  [4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0],
                  [15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13]]
        self.S2 = [[15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10],
                  [3,13,4,7,15,8,14,12,0,1,10,6,9,11,5],
                  [0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15],
                  [13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9]]
        self.S3 = [[10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8],
                  [13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1],
                  [13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7],
                  [1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12]]
        self.S4 = [[7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15],
                  [13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9],
                  [10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4],
                  [3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14]]
        self.S5 = [[2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9],
                  [14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6],
                  [4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14],
                  [11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3]]
        self.S6 = [[12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11],
                  [10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8],
                  [9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6],
                  [4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13]]
        self.S7 = [[4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1],
                  [13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6],
                  [1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2],
                  [6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12]]
        self.S8 = [[13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7],
                  [1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2],
                  [7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8],
                  [2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11]]
        self.S_TABLE = [self.S1,self.S2,self.S3,self.S4,self.S5,self.S6,self.S7,self.S8]
        self.LAST_P = [16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25]

    def pemutation(self,input,K):
        res = []
        for i in K:
            res.append(input[i-1])
        return res

    def shiftBit(self,input,order):
        order %= 16
        input_half1 = input[0:28]
        input_half2 = input [28:56]
        b1 = input_half1[self.SHIFT_ORDER[order]:28] + input_half1[0:self.SHIFT_ORDER[order]]
        b2 = input_half2[self.SHIFT_ORDER[order]:28] + input_half2[0:self.SHIFT_ORDER[order]]
        return b1+b2

    def gen_each_key(self,order):
        per_56 = self.pemutation(self.key,self.PC1)
        shifted = self.shiftBit(per_56,order)
        return self.pemutation(shifted,self.PC2)

    def gen_all_key(self):
        for i in range(0,17):
            self.key_code.append(self.gen_each_key(i))
            
    def R0_L0_cal(self):
        init_pemu_56bits = self.pemutation(self.data,self.IP)
        self.R_n = [init_pemu_56bits[32:64]]
        self.L_n =[init_pemu_56bits[0:32]]

    def xor(self,left,right,numeles):
        res = []
        for i in range(0,numeles):
            if (left[i] == 0 and right[i] == 0): res.append(0)
            elif (left[i] == 1 and right[i] == 0): res.append(1)
            elif (left[i] ==  0 and right[i] == 1): res.append(1)
            elif (left[i] == 1 and right[i] == 1): res.append(0)
        return res

    def part_of_6(self,group_6,K):
        row = group_6[0]*2 + group_6[5]
        col = group_6[1]*8 + group_6[2]*4 + group_6[3]*2 + group_6[4]
        index = self.S_TABLE[K-1][row][col]
        x = bin(index).replace("0b", "")
        x = x[::-1] #this reverses an array
        while len(x) < 4:   
            x += '0'
        bnr = x[::-1]
        return [ord(i) - 48 for i in bnr]

    def P_aka_Srink(self,input_Rx48):
        res = []
        for i in range(0,8):
            a = input_Rx48[i*6:i*6+6]
            res += self.part_of_6(a,i+1)
        return res

    def gen_L_R(self):
        self.R0_L0_cal()
        for i in range(0,16):
            self.L_n.append(self.R_n[i])
            self.R_n.append(self.R_n_caculation(i,self.L_n[i],self.R_n[i]))

    
    def R_n_caculation(self,N,L_Nsub1,R_Nsub1):
        #Rn = Ln-1 xor f(Rn-1,Kn)
        #extend
        extend_48 = self.pemutation(R_Nsub1,self.E)
        # first xor
        xor1 = self.xor(extend_48,self.key_code[N],48)
        # shirnk
        xor1_shrink = self.P_aka_Srink(xor1)
        # last pemutation
        fin_f = self.pemutation(xor1_shrink, self.LAST_P)
        #finnaly create R_n
        return self.xor(L_Nsub1,fin_f,32)


key_input = [0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,1,0,1,0,0,0,1,0,1,0,1,1,0,0,1,1,1,1,0,0,0,1,0,0,1,1,0,1,0,1,0,1,1,0,0,0,1,1,0,0,1,1,0,0,0,0,1,0,0]
data_input = [0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,1,0,1,0,0,0,1,0,1,0,1,1,0,0,1,1,1,1,0,0,0,1,0,0,1,1,0,1,0,1,0,1,1,1,1,0,0,1,1,0,1,1,1,1,0,1,1,1,1]

a = Solution(data_input,key_input)
a.R0_L0_cal()
a.gen_all_key()
a.gen_L_R()
print(a.R_n[0])
print(a.R_n[1])

c=a.pemutation(a.R_n[0]+a.R_n[1],a.IP_minus)
print(c)

#K1 011110000010011000001001001000010110101000100010
