import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.Scanner;  // Import the Scanner class


public class Lab03_1 {
    public static void main(String args[]) {
        Lab03_1 a = new Lab03_1();
        int choice;
        String inputfile = "";
        int isDecrypt = 0;
        int inputfileChoice;
        System.out.println("1. DES/ECB/PKCS5Padding");
        System.out.println("2. DES/ECB/NoPadding");
        System.out.println("3. DES/CBC/PKCS5Padding");
        System.out.println("4. DES/CBC/NoPadding");
        Scanner Obachan = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter choice");
        choice = Obachan.nextInt();  // Read user input
        System.out.println("1.10MB");
        System.out.println("2.100MB");
        System.out.println("3.1GB");
        inputfileChoice = Obachan.nextInt(); 
        System.out.println("Encrypt or decrypt: 1.Yes | 0.No");
        isDecrypt = Obachan.nextInt();  
        System.out.println("Enter key (8 char):");
        String key = Obachan.next();  // Read user input
        String encryp_des = "encrypt.des";
        String decryp_des = "decrypt.doc";
        String pretty;
        switch (inputfileChoice) {
            case 1:
                inputfile = "10MB.bin";
                break;
            case 2:
                inputfile = "100MB.bin";
                break;
            case 3:
                inputfile = "1GB.bin";
                break;
            default:
                inputfile = "10MB.bin";
                break;
        }
        switch (choice) {
             case 1:
                 pretty = "DES/ECB/PKCS5Padding";
                 break;
             case 2:
                 pretty = "DES/ECB/NoPadding";
                 break;
             case 3:
                 pretty = "DES/CBC/PKCS5Padding";
                 break;
             case 4:
                 pretty = "DES/CBC/NoPadding";
                 break;
             default:
                 pretty = "try again";
                 break;
        }
        long t= System.currentTimeMillis();
        a.load_des(choice,inputfile,isDecrypt,encryp_des,decryp_des,key);
        long t1 = System.currentTimeMillis()-t;
        System.out.println("Benchmarking:\n\t"+ pretty + " took "+ t1 + " ms");
    }   
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
    void load_des(int choice,String inputfile,int isDecrypt,String encyp_des,String decryp_des,String key)
    {
        switch (choice) {
            case 1:
            {
                var c = new des_ecb_pkcs5();
                if(isDecrypt == 0){
                    c.encrypt(inputfile,encyp_des,key);
                }
                else{
                    c.decrypt(encyp_des,decryp_des,key);
                }
            }
            break;
            case 2:
            {
                var c = new des_ecb_noPadding();
                if(isDecrypt == 0){
                    c.encrypt(inputfile,encyp_des,key);
                }
                else{
                    c.decrypt(encyp_des,decryp_des,key);
                }
            }
            break;
            case 3:
            {
                var c = new des_cbc_pcks5();
                if(isDecrypt == 0){
                    c.encrypt(inputfile,encyp_des,key);
                }
                else{
                    c.decrypt(encyp_des,decryp_des,key);
                }
            }
            break;
            case 4:
            {
                var c = new des_cbc_noPadding();
                if(isDecrypt == 0){
                    c.encrypt(inputfile,encyp_des,key);
                }
                else{
                    c.decrypt(encyp_des,decryp_des,key);
                }
            }
            break;
            default:
            {
                var c = new des_ecb_pkcs5();
                if(isDecrypt == 0){
                    c.encrypt(inputfile,encyp_des,key);
                }
                else{
                    c.decrypt(encyp_des,decryp_des,key);
                }
            }
                break;
        }
       
    }
    public class des_ecb_pkcs5 {
        public void encrypt(String input,String destination_file,String Key)
        {
            if (input == "") {
                System.out.println("Usage: java EncryptFile <file name>");
                System.exit(-1);
            }
            try  {
                File desFile = new File(destination_file); // create a new file
                FileInputStream fis;
                FileOutputStream fos;
                CipherInputStream cis;
                
                // Create a Secret key
                byte key[] = Key.getBytes(); 
                SecretKeySpec secretKey = new SecretKeySpec(key,"DES");
        
                // Create Cipher object
                Cipher encrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
                encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
                
                // Open the Plaintext file
                try {
                    fis = new FileInputStream(input);
                    cis = new CipherInputStream(fis, encrypt);
                
                    // Write to the Encrypted file
                    fos = new FileOutputStream(desFile);
                    byte[] b = new byte[8];
                    int i = cis.read(b);
                    while (i != -1) {
                        fos.write(b, 0, i);
                        i = cis.read(b);
                    }
                    fos.flush();
                    fos.close();
                    cis.close();
                    fis.close();
                } catch(IOException err) {
                    System.out.println("Cannot open file!");
                    System.exit(-1);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        public void decrypt(String encrypted_fileName,String output_file,String Key)
        {
            try {
                File desFile = new File(encrypted_fileName);
                File desFileBis = new File(output_file);
                FileInputStream fis;
                FileOutputStream fos;
                CipherInputStream cis;
           
                // Create a Secret key
                byte key[] = Key.getBytes(); 
                SecretKeySpec secretKey = new SecretKeySpec(key,"DES");
           
                // Create Cipher object
                Cipher decrypt = Cipher.getInstance("DES/ECB/PKCS5Padding");
                decrypt.init(Cipher.DECRYPT_MODE, secretKey); 
           
                // Open the Encrypted file
                fis = new FileInputStream(desFile);
                cis = new CipherInputStream(fis, decrypt); 
           
                // Write to the Decrypted file
                fos = new FileOutputStream(desFileBis);
                byte[] b = new byte[8];
                int i = cis.read(b);
                while (i != -1) {
                     fos.write(b, 0, i);
                     i = cis.read(b);
                }
                fos.flush();    
                fos.close();    
                cis.close();
                fis.close();
                }  catch(Exception e){
                     e.printStackTrace();
            }
        }
    }
    public class des_ecb_noPadding {
        public void encrypt(String input,String destination_file,String Key)
        {
            if (input == "") {
                System.out.println("Usage: java EncryptFile <file name>");
                System.exit(-1);
            }
            try  {
                File desFile = new File(destination_file); // create a new file
                FileInputStream fis;
                FileOutputStream fos;
                CipherInputStream cis;
                
                // Create a Secret key
                byte key[] = Key.getBytes(); 
                SecretKeySpec secretKey = new SecretKeySpec(key,"DES");
        
                // Create Cipher object
                Cipher encrypt = Cipher.getInstance("DES/ECB/NoPadding");
                encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
                
                // Open the Plaintext file
                try {
                    fis = new FileInputStream(input);
                    cis = new CipherInputStream(fis, encrypt);
                
                    // Write to the Encrypted file
                    fos = new FileOutputStream(desFile);
                    byte[] b = new byte[8];
                    int i = cis.read(b);
                    while (i != -1) {
                        fos.write(b, 0, i);
                        i = cis.read(b);
                    }
                    fos.flush();
                    fos.close();
                    cis.close();
                    fis.close();
                } catch(IOException err) {
                    System.out.println("Cannot open file!");
                    System.exit(-1);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        public void decrypt(String encrypted_fileName,String output_file,String Key)
        {
            try {
                File desFile = new File(encrypted_fileName);
                File desFileBis = new File(output_file);
                FileInputStream fis;
                FileOutputStream fos;
                CipherInputStream cis;
           
                // Create a Secret key
                byte key[] = Key.getBytes(); 
                SecretKeySpec secretKey = new SecretKeySpec(key,"DES");
           
                // Create Cipher object
                Cipher decrypt = Cipher.getInstance("DES/ECB/NoPadding");
                decrypt.init(Cipher.DECRYPT_MODE, secretKey); 
           
                // Open the Encrypted file
                fis = new FileInputStream(desFile);
                cis = new CipherInputStream(fis, decrypt); 
           
                // Write to the Decrypted file
                fos = new FileOutputStream(desFileBis);
                byte[] b = new byte[8];
                int i = cis.read(b);
                while (i != -1) {
                     fos.write(b, 0, i);
                     i = cis.read(b);
                }
                fos.flush();    
                fos.close();    
                cis.close();
                fis.close();
                }  catch(Exception e){
                     e.printStackTrace();
            }
        }
    }
    public class des_cbc_pcks5 {
        public void encrypt(String input,String destination_file,String Key)
        {
            if (input == "") {
                System.out.println("Usage: java EncryptFile <file name>");
                System.exit(-1);
            }
            try  {
                File desFile = new File(destination_file); // create a new file
                FileInputStream fis;
                FileOutputStream fos;
                CipherInputStream cis;
                
                // Create a Secret key
                byte key[] = Key.getBytes(); 
                SecretKeySpec secretKey = new SecretKeySpec(key,"DES");
        
                // Create Cipher object
                Cipher encrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
                encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
                
                // Open the Plaintext file
                try {
                    fis = new FileInputStream(input);
                    cis = new CipherInputStream(fis, encrypt);
                
                    // Write to the Encrypted file
                    fos = new FileOutputStream(desFile);
                    byte[] b = new byte[8];
                    int i = cis.read(b);
                    while (i != -1) {
                        fos.write(b, 0, i);
                        i = cis.read(b);
                    }
                    fos.flush();
                    fos.close();
                    cis.close();
                    fis.close();
                } catch(IOException err) {
                    System.out.println("Cannot open file!");
                    System.exit(-1);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        public void decrypt(String encrypted_fileName,String output_file,String Key)
        {
            try {
                File desFile = new File(encrypted_fileName);
                File desFileBis = new File(output_file);
                FileInputStream fis;
                FileOutputStream fos;
                CipherInputStream cis;
           
                // Create a Secret key
                byte key[] = Key.getBytes(); 
                SecretKeySpec secretKey = new SecretKeySpec(key,"DES");
           
                // Create Cipher object
                Cipher decrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
                decrypt.init(Cipher.DECRYPT_MODE, secretKey); 
           
                // Open the Encrypted file
                fis = new FileInputStream(desFile);
                cis = new CipherInputStream(fis, decrypt); 
           
                // Write to the Decrypted file
                fos = new FileOutputStream(desFileBis);
                byte[] b = new byte[8];
                int i = cis.read(b);
                while (i != -1) {
                     fos.write(b, 0, i);
                     i = cis.read(b);
                }
                fos.flush();    
                fos.close();    
                cis.close();
                fis.close();
                }  catch(Exception e){
                     e.printStackTrace();
            }
        }
    }

    public class des_cbc_noPadding {
        public void encrypt(String input,String destination_file,String Key)
        {
            if (input == "") {
                System.out.println("Usage: java EncryptFile <file name>");
                System.exit(-1);
            }
            try  {
                File desFile = new File(destination_file); // create a new file
                FileInputStream fis;
                FileOutputStream fos;
                CipherInputStream cis;
                
                // Create a Secret key
                byte key[] = Key.getBytes(); 
                SecretKeySpec secretKey = new SecretKeySpec(key,"DES");
        
                // Create Cipher object
                Cipher encrypt = Cipher.getInstance("DES/CBC/NoPadding");
                encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
                
                // Open the Plaintext file
                try {
                    fis = new FileInputStream(input);
                    cis = new CipherInputStream(fis, encrypt);
                
                    // Write to the Encrypted file
                    fos = new FileOutputStream(desFile);
                    byte[] b = new byte[8];
                    int i = cis.read(b);
                    while (i != -1) {
                        fos.write(b, 0, i);
                        i = cis.read(b);
                    }
                    fos.flush();
                    fos.close();
                    cis.close();
                    fis.close();
                } catch(IOException err) {
                    System.out.println("Cannot open file!");
                    System.exit(-1);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        public void decrypt(String encrypted_fileName,String output_file,String Key)
        {
            try {
                File desFile = new File(encrypted_fileName);
                File desFileBis = new File(output_file);
                FileInputStream fis;
                FileOutputStream fos;
                CipherInputStream cis;
           
                // Create a Secret key
                byte key[] = Key.getBytes(); 
                SecretKeySpec secretKey = new SecretKeySpec(key,"DES");
           
                // Create Cipher object
                Cipher decrypt = Cipher.getInstance("DES/CBC/NoPadding");
                decrypt.init(Cipher.DECRYPT_MODE, secretKey); 
           
                // Open the Encrypted file
                fis = new FileInputStream(desFile);
                cis = new CipherInputStream(fis, decrypt); 
           
                // Write to the Decrypted file
                fos = new FileOutputStream(desFileBis);
                byte[] b = new byte[8];
                int i = cis.read(b);
                while (i != -1) {
                     fos.write(b, 0, i);
                     i = cis.read(b);
                }
                fos.flush();    
                fos.close();    
                cis.close();
                fis.close();
                }  catch(Exception e){
                     e.printStackTrace();
            }
        }
    }
}
    

