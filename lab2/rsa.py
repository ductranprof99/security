import math
def genKey(p,q,e):
    n = p*q
    phi = (p-1)*(q-1)
    i = 0;
    hoora = True
    while(hoora):
        i+= 1
        if((i*phi-1)%e == 0):
            hoora = False
    d = int((i*phi-1)/e )

    return (n,e),(d,e)

def encryptMessage(publicKey,plaintext):
    n = publicKey[0]
    e = publicKey[1]
    if(plaintext < n):
        return int(pow(plaintext,e) % n)

def decryptMessage(privateKey,Message):
    d = privateKey[0]
    e = privateKey[1]
    return int(pow(Message,d)%e)

def RSA(p = 0,q = 0,e = 0,M = 0,key = 0):
    # gen data , but we make it inputable
    if(p == 0):
        print("Nhap p ")
        p = int(input())
    if(q == 0):
        print("Nhap q ")
        q = int(input())
    if(e == 0):
        print("Nhap e ")
        e = int(input())
    publicKey,privateKey = genKey(p,q,e)
    print(privateKey)
    if (M == 0):
        print("Nhap M ")
        M = int(input())
    res = 0
    print('1. encryptMessage')
    print('2. decryptMessage')
    if(key == 0):
        key = int(input())
    if(key == 1):
        res = encryptMessage(publicKey,M)
    elif(key == 2):
        res = decryptMessage(privateKey,M)
    return res
def RSA_Bai1():
    print("Nhap p ")
    p = int(input())
    print("Nhap q ")
    q = int(input())
    print("Nhap e ")
    e = int(input())
    print("Nhap M ")
    M = int(input())
    print('========> encrypt:',RSA(p,q,e,M,1))
    print('========>decrypt:',RSA(p,q,e,M,2))

def findPQ_withN(n):
    sxxd = int(pow(n,1/2))+20
    Esstras = [2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53, 59, 61, 67, 71, 73, 79, 83, 89, 97]
    for i in range (2,sxxd):
        if(i in Esstras):
            if(n % i == 0):
                return i,int(n/i)
        else:
            for primeNum in Esstras:
                if i % primeNum == 0:
                    break
                elif(primeNum > int(pow(i,1/2))):
                    Esstras.append(i)
                    break
    return False ,False  
def decrypt_with_n(n,e,M):
    p,q = findPQ_withN(n)
    print(RSA(p,q,e,M,2))
def RSA_bai4():
    print("Nhap e ")
    e = int(input())
    print("Nhap n ")
    n = int(input())
    print("Nhap C ")
    C = int(input())
    decrypt_with_n(n,e,C)

RSA_bai4()
