# TicketMachine-Interface
Human-Computer Interaction Project: Create a new user friendly interface for ticket machines of athens public transportation (O.A.S.A)

Για την εκτέλεση του Project, ακολουθούμε με τη σειρά τα εξής βήματα:
1.	Τοποθετούμε το mongo-java-driver-3.5.0.jar στο dir C:// (Αν προστεθεί σε άλλο dir, θα πρέπει να αλλάξει το path στo project structure> libraries του intelijκαι στο grandle.build του android studio)
2.	Ανοίγουμε backend από Intelij ή άλλο περιβάλλον και τρέχουμε την main τηε κλάσης Server
3.	Ανοίγουμε το frontend μέσω Android Studio. Αν θέλουμε να τρέξουμε την εφαρμογή στον emulator του android studio, αφήνουμε την String MainServerIp όπως έχει (file: MainActivity.java, line:38). Αν θέλουμε να τρέξουμε την εφαρμογή σε πραγματική συσκευή android,αλλάζουμε την String MainServerIp στην local ip του υπολογιστή που τρέχει το backend. (η αλλαγή της String MainServerIp πρέπει να γίνει στο αρχείο MainActivity.java στην γραμμή 38)
