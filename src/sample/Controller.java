package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.lang.Character;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Controller {

    @FXML
    TextField inputFile;
    @FXML
    TextField var;
    @FXML
    static Stage stage = new Stage();


    public void stepByStep(ActionEvent actionEvent) throws FileNotFoundException {
        String flow=readFile();//текст нижнього регістру, тільки за заданим алфавітом
        Map objects=fillNumbers(flow);//порахованні
        System.out.println(objects.toString());



    }

    private HashMap fillNumbers(String flow) {
        int temp;
        Map liter=new HashMap<Character,Integer>();
        for (int i = 0; i < flow.length(); i++) {
            if(liter.containsKey(flow.charAt(i)))
            {
                temp= (int) liter.get(flow.charAt(i));
                liter.put(flow.charAt(i), ++temp);
            }
            else
            {
                liter.put(flow.charAt(i), 1);
            }
        }
        return (HashMap) liter;
    }







    private String readFile() throws FileNotFoundException {
        String flow = "";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Відкрити файл для шифрації");
        //fileChooser.showOpenDialog(stage);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            inputFile.setText(file.getPath());
            try (FileReader reader = new FileReader(file)) {
                int c;
                while ((c = reader.read()) != -1) {
                    flow = flow + (char) c;
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            flow = flow.toLowerCase();
            flow=delNotUkr(flow);
        }
        return flow;
    }

    private String delNotUkr(String flow){
        String newFlow="";
        char []alpha={'а','б','в','г','д','е','є','ж','з','и','і','ї','й','к','л','м','н','о','п','р','с','т','у','ф','х','ц','ч','ш','щ','ь','ю','я','0','1','2','3','4','5','6','7','8','9',' ','?','.',',','\n'};
        for (int i = 0; i < flow.length(); i++) {
            for (int j = 0; j < alpha.length; j++) {
                if(alpha[j]==flow.charAt(i)){
                    newFlow=newFlow+flow.charAt(i);
                }
            }
        }
        return newFlow;
    }
}