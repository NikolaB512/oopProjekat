
package reli;


public class Trkac extends Template implements Korisno{
    private static int id=0;
    private int id_trkaca;
    private Tim tim;


    public Trkac(String naziv) {
        this.id_trkaca = super.dodajIndeks(id++);
        super.naziv = prvoSlovo(naziv);
        this.tim=null;
    }
    public Trkac(int id_trkaca, String naziv)
    {
        this.id_trkaca = id_trkaca;
        super.naziv = prvoSlovo(naziv);
        this.tim=null;
    }

    public Trkac() {
        this.id_trkaca = super.dodajIndeks(id++);
        this.tim=null;
    }
    

    public int getId_trkaca() {
        return id_trkaca;
    }
    
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Tim getTim() {
        return tim;
    }

    public void setTim(Tim tim) {
        this.tim = tim;
    }
    
    public void azurirajID(int novi_id){
        id=novi_id;
    }
    
    @Override
    public String toString() {
        String format="";
        if (tim!=null) {
            format= String.format("%-2d %-15s %-10s",this.id_trkaca,super.naziv, tim.getNaziv());
            return format;
        }
        format= String.format("%-2d %-15s",this.id_trkaca,super.naziv);
        return format;
    }
    
    public String sazetString(){
        String format="";
        if (tim!=null) {
            format= String.format("[%d %s] ",this.id_trkaca,super.naziv);
            return format;
        }
        format= String.format("%d %s",this.id_trkaca,super.naziv);
        return format;
    }

    @Override
    public String prvoSlovo(String naziv) {
        String ime;
        ime = naziv.substring(0,1).toUpperCase()+naziv.substring(1);
        return ime;
    }
}
