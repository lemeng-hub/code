import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class AlibabaLoginTest {
    /** ps: 引入js*/
        /* String js = "document.getElementById(\"nc_1_n1z\").style.left='322px';";
         ((ChromeDriver) driver).executeScript(js);*/


    /**
     * 获取cookie
     */
    @Test
    public void test() {

        System.setProperty("webdriver.chrome.driver", "D:\\myReview-Java\\chromedriver.exe");//chromedriver服务地址
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.zhihu.com/");
//      driver.get("http://47.100.62.154/");
        Set<Cookie> coo = driver.manage().getCookies();
        Iterator it = coo.iterator();
        while (it.hasNext()) {
            System.out.println(it.next() + " ");
        }


//        driver.quit();

    }

    @Test
    public void test2() {
        System.setProperty("webdriver.chrome.driver", "D:\\myReview-Java\\chromedriver.exe");//chromedriver服务地址
        WebDriver driver = new ChromeDriver();
        driver.get("https://login.51job.com/login.php");
        Set<Cookie> coo = driver.manage().getCookies();
        Iterator it = coo.iterator();
        while (it.hasNext()) {
            System.out.println(it.next() + " ");
        }
        System.out.println("------------------------");
        // 下面开始完全模拟正常人的操作，所以你会看到很多 sleep 操作
        WebElement usernameElement = driver.findElement(By.id("loginname"));
        // 模拟用户点击用户名输入框
        usernameElement.click();
        String username = "0000";// 你的手机号
        String password = "0000";// 你的密码
        Random rand = new Random();
        try {
            for (int i = 0; i < username.length(); i++) {
                Thread.sleep(rand.nextInt(1000));// 随机睡眠0-1秒
                // 逐个输入单个字符
                usernameElement.sendKeys("" + username.charAt(i));
            }
            WebElement passwordElement = driver.findElement(By.id("password"));
            passwordElement.click();
            // 输入完成用户名后，随机睡眠0-3秒
            Thread.sleep(rand.nextInt(2000));
            for (int i = 0; i < password.length(); i++) {
                Thread.sleep(rand.nextInt(1000));
                passwordElement.sendKeys("" + password.charAt(i));
            }
            Thread.sleep(1000);
            driver.findElement(By.id("login_btn")).click();
            Thread.sleep(rand.nextInt(1000));
            // 模拟点击登录电脑版
//            WebElement aboutLink = driver.findElement(By.linkText("电脑版"));
//            aboutLink.click();

            Thread.sleep(1000);
            Set<Cookie> coo1 = driver.manage().getCookies();
            Iterator it1 = coo1.iterator();
            while (it1.hasNext()) {
                System.out.println(it1.next() + " ");
            }
            driver.quit();
            Thread.sleep(1000);
            WebDriver driver1 = new ChromeDriver();
            while (it1.hasNext()) {
                driver1.manage().addCookie((Cookie) it1.next());

            }
            driver1.get("https://i.51job.com/userset/my_51job.php");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(50000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        driver.quit();

    }

    /**
     * selenium 模拟登录并获取cookie 写入与读出
     */
    @Test
    public void test3() {

        try {
            this.wtriteC();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.getC();
    }


    /**
     * 获取cookie 并写入文件
     */

    public void wtriteC() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\myReview-Java\\chromedriver.exe");//chromedriver服务地址
        WebDriver dr = new ChromeDriver();
        dr.get("https://passport.alibaba.com/icbu_login.htm");
        WebElement usernameElement = dr.findElement(By.id("fm-login-id"));
        // 模拟用户点击用户名输入框
//    usernameElement.click();
        String username = "0000";// 你的手机号
        String password = "0000";// 你的密码
        Random rand = new Random();
        for (int i = 0; i < username.length(); i++) {
            Thread.sleep(rand.nextInt(1000));// 随机睡眠0-1秒
            // 逐个输入单个字符
            usernameElement.sendKeys("" + username.charAt(i));
        }
        WebElement passwordElement = dr.findElement(By.id("fm-login-password"));
        passwordElement.click();
        // 输入完成用户名后，随机睡眠0-3秒
        Thread.sleep(rand.nextInt(2000));
        for (int i = 0; i < password.length(); i++) {
            Thread.sleep(rand.nextInt(1000));
            passwordElement.sendKeys("" + password.charAt(i));
        }
        Thread.sleep(6000);
        dr.findElement(By.id("fm-login-submit")).click();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File file = new File("E:/JavaTestFile/TestCookies.txt");
        Set<Cookie> s = dr.manage().getCookies();
        if (file.exists()) {
            file.delete();
            try {
                file.createNewFile();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Cookie c : s) {
                bw.write(c.getDomain() + ";" + c.getName() + ";" + c.getValue() + ";" + c.getPath() + ";" + c.getExpiry() + ";" + c.isSecure());
                bw.newLine();
            }
            bw.flush();
            bw.close();
            fw.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
        Thread.sleep(5000);
        dr.quit();
    }

    /**
     * 从文件中获取cookie 并与 url 访问网页
     */
    public void getC() {
        System.setProperty("webdriver.chrome.driver", "D:\\myReview-Java\\chromedriver.exe");//chromedriver服务地址
        WebDriver dr = new ChromeDriver();
        dr.get("https://login.taobao.com/");
        File file = new File("E:/JavaTestFile/TestCookies.txt");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] sz = line.split(";");
                String doman = sz[0].trim();
                String name = sz[1].trim();
                String value = sz[2].trim();
                String path = sz[3].trim();
                Date date = null;
                if (!(sz[4].equals("null"))) {
                    date = new Date(sz[4]);
                    //System.out.println("date="+date);
                }
                Boolean bl = Boolean.valueOf(sz[5]);
                Cookie ck = new Cookie(name, value, doman, path, date, bl);
                dr.manage().addCookie(ck);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
//      刷新网页
        dr.navigate().refresh();
        //dr.quit();
    }

}