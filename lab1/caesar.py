dic = {'A':8.05,'B':1.62,'C':3.2,'D':3.65,'E':12.31,'F':2.28,'G':1.61,'H':5.14,
        'I':7.18,'J':0.1,'K':0.52,'L':4.03,'M':2.25,'N':7.19,'O':7.94,'P':2.29,'Q':0.2,
        'R':6.03,'S':6.59,'T':9.59,'U':3.1,'V':0.93,'W':2.030,'X':0.2,'Y':1.88,'Z':0.09}
highest_rank = list(dict(sorted(dic.items(),key=lambda item: item[1])))[-10:][::-1]
def decypher(input_cypher):
        lel = []
        a = len(input_cypher)
        immutable = {}
        for i in range(0,26):
                lel.append(input_cypher.count(chr(ord('A')+i)))
                immutable[chr(ord('A')+i)] = round(100.0*float(lel[i]/a),2)
        res = list(dict(sorted(immutable.items(),key=lambda item: item[1])).keys())
        res_fix = res[-5:][::-1]
        shift = ord(res_fix[0]) - ord(highest_rank[0])
        a = shifting(input_cypher,shift)
        possible_case = []
        for i in highest_rank:
                shift = ord(res_fix[0]) - ord(i)
                possible_case.append(shifting(input_cypher,shift))      
        return possible_case
def shifting(input_cypher,shift):
        final_res = ''
        b = 0
        for i in input_cypher:
                b = ord(i)%65+shift
                if(b >= 26): b = 65 + b - 26
                elif(b < 0): b = 91 + b
                else: b = 65 + b
                final_res += chr(b)
        return final_res
#result = decypher("KNXMNSLKWJXMBFYJWGJSIXFIRNYXBTWIKNXMWFSITITAJWMJQRNSLFSDIFD")

def decypher2(text,k):
        a = []
        b = ""
        for i in text:
                a.append(ord(i)-96)
        for x in a:
                c = x - k + 26
                c = c if c < 27 else c - 26
                b+=(chr(96+c))
        return b
                
for k in range(0,25):
        print('k = ' ,k , decypher2('asvphgyt',k))                    






