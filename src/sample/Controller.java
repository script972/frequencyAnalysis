package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.lang.*;
import java.lang.Character;
import java.util.*;
import java.util.List;

public class Controller {

    @FXML
    private TextField inputFile;
    @FXML
    private TextField var;
    @FXML
    static Stage stage = new Stage();
    @FXML
    private WebView webView;
    @FXML
    private LineChart graphics;
    @FXML
    private LineChart GraphGroup;
    @FXML
    private Label fileReady;
    @FXML
    private Label graphReady;
    @FXML
    private AnchorPane bottomSide;
    @FXML
    private BorderPane root;
    @FXML
    private GridPane gridPane;


    public void stepByStep(ActionEvent actionEvent) throws FileNotFoundException {
        String flow=readFile();//текст нижнього регістру, тільки за заданим алфавітом
        Map objects=fillNumbers(flow);//порахованні
        ArrayList liters=DividedCharacter(Integer.parseInt(var.getText()),objects); //diveded liters
        String html=generateHtml(liters, flow);
        bottomContent(liters);
        loadWeb(html);
        fillGraph(liters);

    }

    private void bottomContent(ArrayList liters) {
        Iterator iterator=liters.iterator();
        Figure ob;
        Integer basket;
        String figure;
        Integer number;
        String color="";
        int count=0;
        Label label;
        System.out.println(liters);
        while (iterator.hasNext())
        {
            ob= (Figure) iterator.next();
            figure=ob.getFigure().toString();
            label = new Label(figure);
            color=chooseColor(ob.getBasket());
            label.setStyle("-fx-background-color:"+color+";");

            gridPane.add(label,count,0);
            count++;
        }

    }

    private void fillGraph(ArrayList liters) {
        XYChart.Series series = new XYChart.Series();
        series.setName("Frequency analiz");
        int next=1;
        Iterator iterator=liters.iterator();
        Figure figure;
        while (iterator.hasNext()) {
            figure=(Figure) iterator.next();
            series.getData().add(new XYChart.Data(figure.getFigure().toString(), figure.getNumber()));
        }
        graphics.getData().addAll(series);
        fillGraphBlock(liters);
    }

    private void fillGraphBlock(ArrayList liters) {
        Iterator iterator=liters.iterator();
        Figure figure;
        int numb;
        int count=0;
        HashMap basket=new HashMap<Integer,String>();
        String str;
        while(iterator.hasNext())
        {
            figure=(Figure) iterator.next();
            numb=figure.getBasket();
            if (basket.get(numb)==null)
            {
                 str=figure.getFigure().toString();
                 basket.put(numb,str);
            }else {
                str= (String) basket.get(numb)+figure.getFigure().toString();
                basket.put(numb,str);
            }
        }
        System.out.println(basket);
        HashMap basket1=new HashMap<Integer,Integer>();
        iterator=liters.iterator();
        int num;
        while(iterator.hasNext())
        {
            figure=(Figure)iterator.next();
            numb=figure.getBasket();
            count=figure.getNumber();
            if (basket1.get(numb)==null)
            {
                basket1.put(numb,count);
            }
            else
            {
                num= (int) basket1.get(numb);
                basket1.put(numb,num+count);
            }

        }
        System.out.println("LOLOL1 "+basket1.toString());
        System.out.println("LOLOL2 "+basket.toString());

        XYChart.Series series = new XYChart.Series();
        series.setName("Frequency analize of blocks");
        for (int i = 1; i <= basket.size(); i++) {
            series.getData().add(new XYChart.Data(basket.get(i), basket1.get(i)));
        }

        GraphGroup.getData().addAll(series);
        graphReady.setVisible(true);
    }


    private void loadWeb(String s) {
        /*WebEngine webEngine = webView.getEngine();
        webEngine.load(s);*/
        webView.getEngine().loadContent(s);
        fileReady.setVisible(true);
    }

    private String generateHtml(ArrayList liters, String flow) {
        String html="";
        Iterator iterator;
        Figure ob;
        for (int i = 0; i < flow.length(); i++) {
            iterator=liters.iterator();
            while(iterator.hasNext())
            {
                ob= (Figure) iterator.next();
                if (ob.getFigure()==flow.charAt(i))
                {
                    html=html+"<span style='color:"+chooseColor(ob.getBasket())+"'>"+ob.getFigure().toString()+"</span>";
                }
            }
        }
        outFile("readyHtml.html",html);
        return html;


    }
    
    private String chooseColor(int number)
    {
        switch (number)
        {
            case 1: return  "#00FFFF";
            case 2: return  "#000000";
            case 3: return  "#0000FF";
            case 4: return  "#FF00FF";
            case 5: return  "#808080";
            case 6: return  "#008000";
            case 7: return  "#00FF00";
            case 8: return  "#800000";
            case 9: return  "#000080";
            case 10: return  "#808000";
            case 11: return  "#800080";
            case 12: return  "#FF0000";
            case 13: return  "#C0C0C0";
            case 14: return  "#008080";
            case 15: return  "#FFFFFF";
            case 16: return  "#FFFF00";
            case 17: return  "#ff8600";
            case 18: return  "#607d8b";
            case 19: return  "#83608b";
            case 20: return  "#648b60";
        }
        return "Black";

    }

    private void outFile(String nameOutFile, String text) {
        File file = new File(nameOutFile);

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст у файл
                out.print(text);
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveData(String nameOutFile, String text) {
        try {
            OutputStream outputStream = new FileOutputStream(nameOutFile);
            StringBuilder builder = new StringBuilder(text);
            /*for (Map.Entry<String, String> authEntry : nameOutFile.entrySet()) {
                builder.append(authEntry.getKey())
                        .append(' ')
                        .append(authEntry.getValue())
                        .append('\n');
            }*/
            outputStream.write(builder.toString().getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }


    private ArrayList DividedCharacter(int var, Map objects) {
        Character figure;
        int number=0; int basket=0;
        ArrayList frequency=new ArrayList<Figure>();
        var=var+2;
        int divDiapaz= (int) Math.ceil ((double)objects.size()/(double) var);//number of diapazon
        //sort hashMap by value and pull in List - sortingLiter
        List sortingLiter = new ArrayList(objects.entrySet());
        Collections.sort(sortingLiter, new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> a, Map.Entry<Character, Integer> b) {
                return a.getValue() - b.getValue();
            }
        });
        char c=sortingLiter.get(6).toString().split("=")[0].toString().charAt(0);
        int count=0; int next=1;
        for (int i = 0; i < sortingLiter.size(); i++) {
            figure=sortingLiter.get(i).toString().split("=")[0].toString().charAt(0);
            number= Integer.parseInt(sortingLiter.get(i).toString().split("=")[1].toString());
            frequency.add(new Figure(figure, number,next));
            count++;
            if(count>=divDiapaz) {
                count = 0;
                next++;
            }

        }

        return frequency;
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