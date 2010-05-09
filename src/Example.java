import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Simple HTTP Client, which implementing get and post queries.
 * @author Zhuravskiy Vitaliy
 */
public class Example {

	/**
	 * Метод читает из потока данные и преобразует в строку
	 * @param in - входной поток
	 * @param encoding - кодировка данных
	 * @return - данные в виде строки
	 */
	private String readStreamToString(InputStream in, String encoding) throws IOException {
		StringBuffer b = new StringBuffer();
		InputStreamReader r = new InputStreamReader(in, encoding);
		int c;
		while ((c = r.read()) != -1) {
			b.append((char)c);
		}
		return b.toString();
	}

	public void postExample(String url, QueryString query) throws IOException {
		//устанавливаем соединение
		URLConnection conn = new URL(url).openConnection();
		//мы будем писать POST данные в out stream
		conn.setDoOutput(true);

		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "ASCII");
		out.write(query.toString());
		out.write("\r\n");
		out.flush();
		out.close();

		//читаем то, что отдал нам сервер
		String html = readStreamToString(conn.getInputStream(), "UTF-8");

		//выводим информацию в консоль
		System.out.println("URL:" + url);
		System.out.println("Html:\n" + html);
	}

	public void getExample(String url, QueryString query) throws IOException {
		//устанавливаем соединение
		URLConnection conn = new URL(url + "?" + query).openConnection();
		//заполним header request парамеры, можно и не заполнять
		conn.setRequestProperty("Referer", "http://google.com/http.example.html");
		conn.setRequestProperty("Cookie", "a=1");
		//можно установить и другие парамеры, такие как User-Agent

		//читаем то, что отдал нам сервер
		String html = readStreamToString(conn.getInputStream(), "UTF-8");

		//выводим информацию в консоль
		System.out.println("URL:" + url);
		System.out.println("Html:\n" + html);
	}

	public static void main(String[] args) throws IOException {

		QueryString q = new QueryString()
			.add("login","admin")
			.add("password", "pass");

		Example e = new Example();
	//	e.getExample("http://juravskiy.ru/", q);
		e.postExample("http://juravskiy.ru/", q);
	}
}
