# Codereview
## Checkstyle

- Diverse Typofehler -> Diese wurden behoben oder gegen passenders behoben.
- Es wurden alle Quellcode-Dateien mit JavaDoc-Kommentaren versehen. 
- Zu lange Zeilen in den JavaDoc-Kommentaren -> Die JavaDoc-Kommentare wurden mit passenderen Zeilenumbrüchen versehen.
- Variablen, welche als final deklariert werden sollten, wurden als final deklariert. Es gab Ausnahmen bei solchen, die durch andere Methoden verändert werden. Dies dient einer besseren Kontrollmöglichkeit unsererseits. 

## FindBugs 

- Mögliche Problematiken mit der Synchronisation ->Die betroffenen Variablen wurden überprüft und, wenn notwendig, threadsicher gemacht. 
    Die betroffenen Methoden wurden angepasst. Wir haben, um dies sicherzustellen, die einzelnen Methoden darauf überprüft, ob diese außerhalb eines einzelnen Threads verwendet werden.
    Sollten wir einzelne Bereiche, wie die State Machine, auch in einen einzelnen Thread auslagern, müssen wir die Überprüfung noch einmal durchführen. 
- Bei der State Machine hat Find Bugs Sicherheitsbedenken mit unserer Singleton-Implementation geäußert. Wir haben uns dem angenommen und konnten für uns sicherstellen, dass wir damit keine Probleme bekommen. Wir geben eine static-Variable zurück, welche den Kontruktor bei der Initialisierung aufruft. 

## Sonstiges

- Wir haben bemerkt, dass wir bei einem Refactoring den Methodenaufruf vergessen haben, welcher den State festlegt(updateState()). Den Fahler haben wir behoben. 
- Wir haben bei der UI "Verbesserungsbedarf" festgestellt, sodass wir nun eine praktischere UI implementiert haben. 
- Ansonsten haben wir noch festgestellt, dass ein "großes" Refactoring ganz gut wäre, da unsere Codeseparierung durch diverse Bugfixes schlechter geworden ist. 