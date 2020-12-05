package jeu.save;

import jeu.Main;

import java.io.*;

/**
 * Classe permettant l'ecriture et la lecture des sauveguardes dans un fichier
 */
public class SaveWriteRead {

    /**
     * Supprime le fichier de sauveguarde
     */
    public static void deleteSave(){
        File f = new File(Main.FILE_NAME);
        if(f.delete()){
            System.out.println("Fichier " + f + " supprime avec succes !");
        }else{
            System.out.println("Nous n'avons pas reussit a supprimer le fichier");
        }
    }

    /**
     * Ecrit les sauveguardes dans le fichier
     * @param fileName le nom du fichier
     * @param saveStorage l'object contenant la liste des sauveguardes
     */
    public static void writeFile(String fileName, SaveStorage saveStorage) throws IOException {
        File f = new File(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(saveStorage);
        oos.flush();
        oos.close();
    }

    /**
     * Recupere l'object SaveStorage contenu dans le fichier
     * @param fileName le nom du fichier
     * @return saveStorage l'object contenant la liste des sauveguardes ecrites dans le fichier
     */
    public static SaveStorage readFile(String fileName) throws IOException {
        File f = new File(fileName);
        SaveStorage saveStorage = null;
        if(f.exists()){
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            try{
                saveStorage = (SaveStorage) ois.readObject();
            }catch(Exception e){
                System.out.println("Le fichier de sauvegarde est corrompu !");
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
