package kz.talipovsn.rates;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

// СОЗДАТЕЛЬ КОТИРОВОК ВАЛЮТ
public class RatesReader {

    private static final String BASE_URL = "https://github.com/cryptomator/cryptomator/releases"; // Адрес с котировками

    // Парсинг котировок из формата html web-страницы банка, при ошибке доступа возвращаем null
    public static String getRatesData() {
        StringBuilder data = new StringBuilder();
        try {

              Document doc = Jsoup.connect(BASE_URL).timeout(5000).get();
              data.append("Commits: \n");
              Elements v = doc.select("span.f1");
              Elements c = doc.select("span.Label");
              Elements fc = doc.select("a.commit-link");
              Elements date = doc.select("relative-time.no-wrap");
              Elements l = doc.select("a.color-fg-muted");
              Elements box = doc.select("section");
              for (int i = 0; i < box.size(); i++) {
                  Element v2 = v.get(i);
                  Element c2 = c.get(i);
                  Element fc2 = fc.get(i);
                  String date2 = date.get(i).attr(("datetime"));
                  Element l2 = l.get(i);


                  DateFormat df_GH = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                  DateFormat df_KZ = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                  df_GH.setTimeZone(TimeZone.getTimeZone("UTC"));

                  String date3 = df_KZ.format(df_GH.parse(date2));


                  data.append("Version: " + v2.text() + "\n");
                  data.append("Type: " + c2.text() + "\n");
                  data.append("Ot/Do: " + fc2.text() + "\n");
                  data.append("Date: " + date3 + "\n");
                  data.append("Login: " + l2.text() + "\n\n");
              }
        } catch (Exception e) {
      //      System.out.println(e.toString());
            return null; // При ошибке доступа возвращаем null
        }
        return data.toString().trim(); // Возвращаем результат
    }

}