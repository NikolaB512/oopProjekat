
package reli;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public abstract class Trka {
    public static void pokreniTrku(ArrayList<Tim> timovi)
    {
        ArrayList<Tim> pom = new ArrayList<>(timovi);
        Random rnd = new Random();

        
        int pozicija=1;
        int poeni=24;
        
        while(!pom.isEmpty()){            
            Collections.shuffle(pom);
            System.out.println("Tim " + pom.get(0).getNaziv() + " je zavrsio na " + pozicija + ". mestu. ("+poeni+" poena).");
                        
            if(pozicija==1)
                pom.get(0).dodajPobedu();
            pom.get(0).dodajPoene(poeni);
                
            
            pom.remove(0);
            pozicija++;
            poeni-=3;
        }          
    }
    public static void ispisTabele(ArrayList<Tim> timovi)
    {
        for (int i = 0; i < timovi.size()-1; i++) {
            for (int j = i+1; j < timovi.size(); j++) {
                if ((timovi.get(j).getBr_pobeda()>timovi.get(i).getBr_pobeda()) || ((timovi.get(j).getBr_pobeda()==timovi.get(i).getBr_pobeda()) && (timovi.get(j).getBr_poena()>timovi.get(i).getBr_poena()))) {
                     Collections.swap(timovi, i, j);                    
                }
            }
        }
        String ispis="ID Naziv           Auto   Br pobeda Br poena Clanovi\n";
        for(Tim t: timovi)
        {
            ispis+=t.toString() + "\n";
        }
        System.out.println(ispis);
    }
    public static void sacuvaj(ArrayList<Tim> timovi, ArrayList<Trkac> trkaci)
    {
        JSONArray jsonTimovi=new JSONArray();
        JSONArray jsonTrkaci=new JSONArray();
        PrintWriter pw = null;
        for (Trkac t: trkaci) {
            JSONObject obj = new JSONObject();
            obj.put("id_trkaca", t.getId_trkaca());
            obj.put("naziv", t.getNaziv());       
            if (t.getTim()==null) {
                obj.put("tim", null);
            }
            else
                obj.put("tim", t.getTim().getId_tima());            
            jsonTrkaci.add(obj);
        }
        for(Tim tim:timovi)
        {
            JSONObject obj = new JSONObject();
            obj.put("id_tima", tim.getId_tima());
            obj.put("naziv", tim.getNaziv());     
            obj.put("auto", tim.getAuto().getId_auto());
            obj.put("br_pobeda",tim.getBr_pobeda());
            obj.put("br_poena", tim.getBr_poena());
            JSONArray clanovi = new JSONArray();
            for (Trkac clan: tim.getClanovi()) 
                clanovi.add(clan.getId_trkaca());
            obj.put("clanovi", clanovi);
            jsonTimovi.add(obj);
        }
        try {
            pw=new PrintWriter("takmicari.json");
            pw.write(jsonTrkaci.toJSONString());
            pw.flush();
            pw=new PrintWriter("timovi.json");
            pw.write(jsonTimovi.toJSONString());
        } catch (FileNotFoundException ex) {
            System.out.println("Greska prilikom upisa!\n"+ex.getMessage());
        }
        finally{
            if(pw != null)
                pw.close();
        }
    }
    public static ArrayList<Trkac> procitajTakmicare() throws Exception
    {        
        ArrayList<Trkac> noviTrkaci= new ArrayList<>();
        JSONArray jsonTrkaci=(JSONArray) new JSONParser().parse(new FileReader("takmicari.json"));
        
        for(Object o: jsonTrkaci){
            JSONObject obj=(JSONObject) o;
            String naziv=obj.get("naziv").toString();
            int id_takmicara=Integer.parseInt(obj.get("id_trkaca").toString());
            
            Trkac pom= new Trkac(id_takmicara, naziv);
            pom.azurirajID(pom.getId_trkaca());
            noviTrkaci.add(pom);
        }
        return noviTrkaci;
    }
    public static ArrayList<Tim> procitajTimove(ArrayList<Auto> auti, ArrayList<Trkac> trkaci) throws Exception
    {
        ArrayList<Tim> noviTimovi= new ArrayList<>();
        JSONArray jsonTimovi=(JSONArray) new JSONParser().parse(new FileReader("timovi.json"));
        for(Object o:jsonTimovi){
            JSONObject obj=(JSONObject) o;
            String naziv= obj.get("naziv").toString();
            int id_tima=Integer.parseInt(obj.get("id_tima").toString());
            int br_poena=Integer.parseInt(obj.get("br_poena").toString());
            int br_pobeda=Integer.parseInt(obj.get("br_pobeda").toString());
            int auto=Integer.parseInt(obj.get("auto").toString());
            JSONArray jsonClanovi=(JSONArray)obj.get("clanovi");
            
            Tim pom = new Tim(id_tima,naziv, br_pobeda, br_poena);
            for(Auto a:auti)
            {
                if (auto==a.getId_auto()) {
                    pom.setAuto(a);
                }
            }            
            for(Trkac t: trkaci)
            {
                for (int i = 0; i < jsonClanovi.size(); i++) {
                    if (t.getId_trkaca()==Integer.parseInt(jsonClanovi.get(i).toString())) {
                        pom.dodajTrkaca(t);
                        jsonClanovi.remove(i--);
                    }
                }
            }
            pom.azurirajID(pom.getId_tima());
            noviTimovi.add(pom);
        }        
        return noviTimovi;
    }
}
