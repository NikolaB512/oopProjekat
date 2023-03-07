
package reli;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        ArrayList<Trkac> trkaci = new ArrayList<>();
        ArrayList<Tim> timovi = new ArrayList<>();
        ArrayList<Auto> auti = new ArrayList<>(); 
                        
        auti.add(new Auto("Subaru", "WRX",276,2004));
        auti.add(new Auto("Mitsubishi", "EVO X",280,2007));
        auti.add(new Auto("Citroen", "C4",315,2007));
                
        boolean izlaz=false;
        while(izlaz==false)
        {
            System.out.println("Zapocni trku [R]; Tabela[T]; Dodavanje i Uklanjanje[E]; Snimi[S]; Preuzmi[P]; Izlaz[I]");
            String izbor=scan.next();
            
            switch(izbor.toLowerCase()){
                case "r":
                    Trka.pokreniTrku(timovi);
                    break;
                case "i":
                    izlaz=true;
                    break;
                case "t":
                    Trka.ispisTabele(timovi);
                    break;
                case "s":
                    Trka.sacuvaj(timovi, trkaci);
                    System.out.println("Uspesno sacuvano!");
                    break;
                case "p":                  
                    try {
                        trkaci=Trka.procitajTakmicare();
                        timovi=Trka.procitajTimove(auti,trkaci);
                        System.out.println("Uspesno ucitano!");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }                    
                    break;

                case "e":
                    boolean izlaz_edit=false;
                    while(izlaz_edit==false)
                    {
                        System.out.println("Dodaj novog trkaca[R]; Dodaj novi tim[T]; Lista trkaca[LR]; Lista timova[LT]; Obrisi Trkaca[DR]; Obrisi tim[DT]; Nazad[I]");
                        izbor=scan.next();
                        switch (izbor.toLowerCase())
                        {
                            case "i":
                                izlaz_edit=true;
                                break;
                            case "r":                      
                                boolean postoji_trkac=false;
                                System.out.println("Unesite ime trkaca:");
                                String naziv = scan.next();
                                for(Trkac tr:trkaci)
                                {                                    
                                    if(tr.getNaziv().equalsIgnoreCase(naziv))
                                    {
                                        System.out.println("Vec postoji trkac sa tim imenom!");
                                        postoji_trkac=true;
                                    }  
                                }  
                                if (!postoji_trkac) {
                                    Trkac tr = new Trkac(naziv);
                                    trkaci.add(tr);
                                    System.out.println("Uspesno dodat novi trkac");
                                }                                
                                break;
                            case "t":
                                boolean postoji_tim=false;
                                System.out.println("Unesite ime tima:");
                                String naziv_tima = scan.next();
                                for(Tim tim: timovi)
                                {
                                    if (tim.getNaziv().equalsIgnoreCase(naziv_tima)) {
                                        System.out.println("Vec postoji tim sa tim imenom!");
                                        postoji_tim=true;
                                    }
                                    else if(timovi.size()>8)
                                    {
                                        System.out.println("Maksimalni broj timova je 8");
                                    }
                                }
                                if(!postoji_tim)
                                {
                                    Tim tim = new Tim(naziv_tima);
                                    int opcija_auto;
                                    System.out.println("Izaberite auto za tim:");
                                    for(Auto a:auti)
                                    {
                                        System.out.println("[" + a.getId_auto()+"]"+a.toString());
                                    }
                                    try{
                                        opcija_auto=scan.nextInt();
                                        while(opcija_auto > auti.size() || opcija_auto<0)
                                        {
                                            System.out.println("Nije opcija, unesite opet");
                                            opcija_auto=scan.nextInt();
                                        }    
                                        
                                        tim.setAuto(auti.get(opcija_auto-1));
                                        System.out.println("Izaberite clanove za ubacivanje:");
                                        ArrayList<Trkac> slobodni = new ArrayList<>();
                                        for(Trkac t: trkaci)
                                        {
                                            if (t.getTim()==null) {
                                                slobodni.add(t);
                                                System.out.println(t.toString());
                                            }
                                        }
                                        System.out.println("Unesite id-ove clanova tima(sa zarezima izmedju)");
                                        String[] nizId = scan.next().split(",");
                                        for (int i = 0; i < nizId.length; i++) 
                                        {
                                            for (int j = 0; j < slobodni.size(); j++) {
                                                if (slobodni.get(j).getId_trkaca()== Integer.parseInt(nizId[i])) {              // integer exception
                                                    tim.dodajTrkaca(slobodni.get(j));
                                                    slobodni.remove(j--);                                                
                                                }
                                            }
                                        }
                                        if (!tim.getClanovi().isEmpty()) {
                                            timovi.add(tim);
                                            System.out.println("Tim uspesno dodat!");
                                        }
                                        else
                                            System.out.println("Tim mora da ima clanove!");                                       
                                    }
                                    catch(Exception ex)
                                    {
                                        scan.next();
                                        System.out.println("Netacan unos podataka!");
                                    }       
                                }
                                break;
                            case "lr":
                                System.out.println("ID Ime             Tim");
                                for(Trkac tra:trkaci)
                                   System.out.println(tra.toString());
                                break;
                            case "lt":
                                System.out.println("ID Naziv           Auto   Br pobeda Br poena Clanovi");
                                for(Tim t:timovi)
                                    System.out.println(t.toString());
                                break;
                            case "dr":
                                System.out.println("Ukucaj id trkaca za brisanje:");
                                try{
                                    boolean obrisano = false;
                                    int idDR = scan.nextInt();
                                    for (int i = 0; i < trkaci.size(); i++) {
                                        if (idDR==trkaci.get(i).getId_trkaca()) {
                                            trkaci.get(i).getTim().getClanovi().remove(trkaci.get(i));
                                            trkaci.remove(i--);
                                            System.out.println("Uspesno obrisan!");   
                                            obrisano=true;
                                            break;
                                        }
                                    }
                                    for (int i = 0; i < timovi.size(); i++) {
                                        if (timovi.get(i).getClanovi().isEmpty()) {
                                            timovi.remove(i--);
                                            System.out.println("Izbrisan tim bez clanova");
                                            break;
                                        }
                                    }
                                    if (!obrisano) {
                                        System.out.println("Nista nije obrisano!");
                                    }
                                }
                                catch(Exception ex)
                                {
                                    scan.next();
                                    System.out.println("Netacan unos!");
                                }
                                break;
                            case "dt":
                                System.out.println("Ukucaj id tima za brisanje:");
                                try{
                                    boolean obrisano =false;
                                    int idDT = scan.nextInt();
                                    for (int i = 0; i < timovi.size(); i++) {
                                        if (idDT==timovi.get(i).getId_tima()) {
                                            for(Trkac t: timovi.get(i).getClanovi())
                                            {
                                                t.setTim(null);
                                            }
                                            timovi.remove(i);
                                            System.out.println("Uspesno obrisan!");
                                            obrisano=true;
                                            break;                                                
                                        }                                            
                                    }
                                    if (!obrisano) {
                                        System.out.println("Nista nije obrisano!");
                                    }
                                }  
                                catch(Exception ex){
                                    scan.next();
                                    System.out.println("Netacan unos!");
                                }
                                break;
                            default:
                                System.out.println("ne postoji ta opcija");    
                                break;
                        }  
                    }                                      
                    break;
                    default:
                        System.out.println("ne postoji ta opcija");
            }        
        }        
    }
}
