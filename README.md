Перед началом нужно убедиться, что у тебя стоит:
Wolfram		c:\Program Files\Wolfram Research\Mathematica\9.0\ (если путь друго, то смотри ниже)
Git			c:\Program Files (x86)\Git\
Maven		c:\Program Files\Apache Software Foundation\apache-maven-3.1.1\ (версия может быть другой)

Теперь из любой папки запускаем:
git clone https://github.com/heybai/pseudo-alpha.git

Теперь необходимо добавить JLink в мавеновский репозиторий. Для этого из папки проекта запускаем:
mvn install:install-file -Dfile=lib/JLink.jar -DgroupId=com.wolfram.jlink -DartifactId=jlink -Dversion=4.4.0 -Dpackaging=jar

Если путь к математике отличается от указанного выше, то в UploadFileService.java придется изменить строки 38 и 44.

Теперь собираем и запускаем проект. Для этого из папки проекта выполняем команду:
mvn jetty:run

Если все прошло хорошо и мы увидели заветную надпись "Started Jetty server", то можем смело проверять "http://localhost:8080/index.html"

Для остановки запущенного сервера просто несколько раз нажать Ctrl+C в этой же командной строке
