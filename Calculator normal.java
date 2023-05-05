import java.util.HashMap;       //этот класс нужен чтобы присваивать значениям своё число
import java.util.Map;           //этот класс нужен чтобы с этими значениями взаимодействовать
import java.util.Scanner;       //этот класс нужен чтобы можно было вводить свои данные в программу
import java.util.regex.Pattern; //мама я нашёл ножницы, теперь я смогу работать с целой строкой

public class Calculator2 {     //мне лень снова это комментировать, но это кусок из старой программы, я только удалил две римские цифры потому что калькулятор больше в степень не возводит

    private static final Map<Character, Integer> para = new HashMap<>();
    private static final String[] rimskieChisla = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};

    static {                
        para.put('I', 1);
        para.put('V', 5);
        para.put('X', 10);
        para.put('L', 50);
        para.put('C', 100);

    }

    private static int RA(String rim) { 
        int q = 0;                        
        for (int x = 0; x < rim.length(); ++x) {
            if (x + 1 < rim.length() && para.get(rim.charAt(x)) < para.get(rim.charAt(x + 1))) {
                q -= para.get(rim.charAt(x));
            } else {
                q += para.get(rim.charAt(x));
            }
        }
        return q;
    }

    private static String AR(int arab) { 
        StringBuilder c = new StringBuilder();
        int v = arab;                      
        for (int x = rimskieChisla.length - 1; x >= 0; --x) {
            while (v >= RA(rimskieChisla[x])) {
                c.append(rimskieChisla[x]);
                v -= RA(rimskieChisla[x]);
            }
        }
        return c.toString();
    }

    public static boolean etoArab(String num) { //проверяем арабское ли число создав метод с boolean. Этот метод будет нужен нам попозже
        Pattern chotaArabskoe = Pattern.compile("[0-9]+");
        return chotaArabskoe.matcher(num).matches();
    }

    public static boolean etoRim(String num) { // тоже самое что метод выше только с римскими цифрами
        Pattern chotaRimskoe = Pattern.compile("[IVXLCDM]+");
        return chotaRimskoe.matcher(num).matches();
    }

    public static void main(String[] args) { 
        Scanner s = new Scanner(System.in);

        System.out.print("Введите выражение (например, 4 + 2 или V - IV): "); //теперь нам надо как то впитать всю строку а не посимвольно
        String paravoz = s.nextLine();     //отлично у сканера есть метод позволяющий кушать строки целиком, по идее их теперь надо разбить
        String[] vagon = paravoz.split(" "); //разбиваем с помошью split на условные куски

        if (vagon.length != 3) {                //а это чтобы не вводили в строку неподходящее колличество символов
            System.out.println("я так не умею только 2 цифры пожалуйста.");
            return;
        }

        String chislo3 = vagon[0];          //а вот и те самые куски разбитые с помошью метода split, закидываем их в переменные 
        String chislo4 = vagon[2];
        char vibor = vagon[1].charAt(0);

        if (!etoArab(chislo3) && !etoRim(chislo3) || !etoArab(chislo4) && !etoRim(chislo4)) { //маленькая проверка на совпадение чисел через &&
            System.out.println("любо римские либо арабские, сразу оба нельзя.");
            return;
        }

        int chislo1 = etoArab(chislo3) ? Integer.parseInt(chislo3) : RA(chislo3); //это мы наконец то заливаем наши переменные добытые из строки в старый калькулятор
        int chislo2 = etoArab(chislo4) ? Integer.parseInt(chislo4) : RA(chislo4);
        char type = etoArab(chislo3) ? 'A' : 'R';

        if (chislo1 > 10 || chislo2 > 10) {       // и ещё одна проверка а дальше продолжается наш старый калькулятор
            System.out.println("больше десяти нельзя");
            return;
        }

        int resultat = 0;
        switch (vibor) {
            case '+':
                resultat = chislo1 + chislo2;
                break;
            case '-':
                resultat = chislo1 - chislo2;
                break;
            case '*':
                resultat = chislo1 * chislo2;
                break;
            case '/':
                resultat = chislo1 / chislo2;
                break;
            default:
                System.out.println("Такое я не умею");
                break;
        }

        if (type == 'R' && resultat < 0) {
            System.out.println("Римские числа не бывают отрицательными");
            return;
        }

        if (type == 'A'){
            System.out.println("Результат: " + resultat);
        } else {
            System.out.println("Результат: " + AR(resultat));  
        }
        s.close();
    }
}
   