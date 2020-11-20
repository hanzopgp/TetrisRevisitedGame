package jeu.save;

import java.io.*;

public class SaveWriteRead {

    public static void writeFile(String fileName, SaveStorage saveStorage) throws IOException {
        File f = new File(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(saveStorage);
        oos.flush();
        oos.close();
    }

    public static SaveStorage readFile(String fileName) throws IOException, ClassNotFoundException {
        File f = new File(fileName);
        SaveStorage saveStorage = null;
        if(f.exists()){
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            try{
                saveStorage = (SaveStorage) ois.readObject();
            }catch(Exception e){
                System.out.println("Le fichier de sauveguarde est corrompu !");
                ois.close();
                if(f.delete()){
                    System.out.println("Fichier supprime avec succes !");
                }else{
                    System.out.println("Nous n'avons pas reussit a supprimer le fichier");
                }
            }
            ois.close();
        }
        return saveStorage;
    }

}
