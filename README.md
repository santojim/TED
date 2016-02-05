#	ΑΝΑΠΤΥΞΗ ΛΟΓΙΣΜΙΚΟΥ ΓΙΑ ΔΙΚΤΥΑ ΚΑΙ ΤΗΛΕΠΙΚΟΙΝΩΝΙΕΣ 
## 3ο Παραδοτέο

**Ανδρέας Οικονομάκης**	sdi0800272

**Δημήτρης Σαντοριναίος** sdi0800282

### Android

#### activities

  - Αρχείο `MainActivity` : Είναι το πρώτο Activity που εκτελείται, δημιουργεί την Cache, ελέγχει αν υπάρχει σύνδεση στο διαδίκτυο, αν υπάρχει επικοινωνία με τον server, και αναλαβμάνει την διαδικασία Login. Επίσης δημιουργεί το `SharedPreferences` xml και αποθηκεύει το username, το password και την Ip που έδωσε ο χρήστης. Σε περίπτωση που ο χρήστης κάνει `logout` το username και το password διαγράφονται. Σε περίπτωση εισαγωγής λανθασμένων στοιχείων εμφανίζεται κατάλληλο μήνυμα. Εάν ο χρήστης δεν έχει λογαριασμό, πατώντας το κουμπί `REGISTER NOW` δημιουργείται το `RegisterActivity` και ο χρήστης μεταφέρεται εκεί. Στο `Action Bar` της `MainActivity`, υπάρχει μενού με επιλογές Configure Ip και Turn Off. Πατώντας Configure Ip ο χρήστης μεταφέρεται στο `ConfigureIpFragment`, ενώ πατώντας Turn Off ή το `Android Back Button` η εφαρμογή τερματίζει.

  - Αρχείο `LoggedinActivity` : Δημιουργείται από το `MainActivity` με την επιτυχή εισαγωγή στοιχείων του χρήστη. Ο χρήστης μπορεί να επιλέξει ανάμεσα στα `fragments` που αναφέρονται παρακάτω. Η λίστα που περιέχει τα `fragments` ανοίγει/κλείνει με δύο τρόπους, ο πρώτος είναι πατώντας το σύμβολο λίστας-μενού αριστερά στο `Action Bar`, ενώ ο δεύτερος είναι σέρνοντας το δάκτυλο απο τα αριστερά της οθόνης προς τα δεξιά (`swipe right gesture`). Στα δεξιά στο `Action Bar` υπάρχει το σύμβολο των ρυθμίσεων. Πατώντας στις ρυθμίσεις ο χρήστης έχει δύο επιλογές, είτε να κλείσει την εφαρμογή είτε να κάνει `logout`. Πατώντας το `Android Back Button` εμφανίζεται μήνυμα που ενημερώνει τον χρήστης αν θέλει να τερματίσει την εφαρμογή. Πατώντας `ναι` η εφαρμογή τερματίζει, ενώ στο `όχι` συνεχίζει να λειτουργεί.

  - Αρχείο `RegisterActivity` : Δημιουργείται από το `MainActivity` με το πάτημα του κουμπιού `REGISTER NOW`. Ο χρήστης μεταφέρεται σε μια οθόνη όπου του ζητείται να εισάγει το username και password. Σε περίπτωση που αφήσει κενό κάποιο πεδίο εμφανίζεται αντίστοιχο μήνυμα. Σε περίπτωση που το username που συμπληρώσει υπάρχει ήδη στον server ενημερώνεται με αντίστοιχο μήνυμα. Εάν τα στοιχεία που συμπληρώσει είναι ορθά, ενημερώνεται η βάση του server και ο χρήστης επιστρέφει στην αρχική οθόνη. Αφού βάλει τα στοιχεία του και πατήσει το κουμπί `LOGIN` εμφανίζεται στον server και ο χρήστης περιμένει εάν ο server τον αποδεχτεί ή τον απορρίψει, όπου ενημερώνεται με το αντίστοιχο μήνυμα.

#### cache
  - Αρχείο `Cache` : Είναι ένα αντικείμενο που περιγράφει τον Client. Περιέχει το αναγνωριστικό κάθε client `String client_id`  , έναν ακέραιο που δείχνει την τρέχουσα κατάσταση `int status` 1 σημαίνει οτι ο Client είναι online , 0 σημαίνει οτι είναι offline. Το πεδίο `int confirmed` δηλώνει εάν τον έχει αποδεχτεί ο server (έχει την τιμή 1 ) ή εαν τον έχει απορρίψει (τιμή 0 ). Το πεδίο `int checked` χρησιμοποιήθηκε ώστε να υπάρχει η πληροφορία ποιους Clients επέλεξε ο χρήστης μέσω των `CheckedTextView` για να εκτελέσει την αντίστοιχη λειτουργία (π.χ. Turn off clients , Send Jobs to Clients , Delete Periodic Job ). Το πεδίο `ArrayList<Job> jobs_running_on_client` είναι μια λίστα που περιέχει αναλυτικά όλες τις δουλειές που τρέχει ο συγκεκριμένος Client. Τέλος το πεδίο `ΑrrayList<Result> results` είναι μια λίστα που περιέχει όλα τα αποτελέσματα που έχουν έρθει από τον συγκεκριμένο Client. Για κάθε πεδίο υπάρχουν οι αντίστοιχες συναρτήσεις set-get. Η τελική Cache που αντιπροσωπεύει το σύνολο των clients είναι μια `ArrayList<Cache>` που δημιουργείται στην MainActivity. 

  - Αρχείο `CacheQueries` : ΑΝΤΡΕΑ ΒΡΩΜΙΑΡΗ ΣΥΜΠΛΗΡΩΝΕ

  - Αρχείο `Job` : Είναι ένα αντικείμενο που περιγράφει τις διεργασίες nmap που θα εισαχθούν σε κάποιον Client. Περιέχει ένα αναγνωριστικό `int job_id` μοναδικό για κάθε job, ένα πεδίο `String parameters` όπου αποθηκεύονται οι παράμετροι του nmap , ένα πεδίο `int periodic` το οποίο περιγράφει εάν η συγκεκριμένη δουλειά είναι περιοδική ( έχει τιμή 1 ) ή όχι (τιμή 0 ), τέλος το πεδίο `int time_periodic` αποθηκεύει τον χρόνο στον οποίο θα εκτελείται περιοδικά η εντολή ( 0 αν δεν είναι περιοδική). Για κάθε πεδίο υπάρχουν οι αντίστοιχες συναρτήσεις set-get. Στην MainActivity δημιουργείται μια `ArrayList<Job>` που περιέχει όσες δουλειές υπάρχουν στον server αλλά και τις δουλειές που μπορεί να εισάγει ή να αφαιρέσει ο χρήστης από το Android device.

  - Αρχείο `Result` : Είναι ένα αντικείμενο που περιγράφει ένα αποτέλεσμα που φτάνει στο Android από κάποιον Client. Κάθε Client δηλαδή κάθε `Cache` έχει μια `ArrayList<Result>` στην οποία αποθηκεύονται όλα τα αποτελέσματά του. Το `Result` έχει τα εξής πεδία : 
    - `String jobID` : Το id από την εντολή που προέρχεται αυτό το αποτέλεσμα.
    - `String ip_scanned` : Η IP που σκάναρε το nmap.
    - `String host` : Το όνομα του server που σκαναρίστηκε.
    - `ArrayList<String> ports` : Λίστα από ports που βρέθηκαν.
    - `ArrayList<String> states` : Λίστα με τις καταστάσεις των ports (open , closed).
    - `ArrayList<String> services` : Λίστα με τα service για κάθε port (http, ftp , ssh , mysql κλπ).

#### database

##### Σχήμα Βάσης
  *PendingClients table :*

    idClient  | accept |
------------- | :----: |
999999999999  | 1      |
888888888888  | 0      |

  *TurnOffClients table :*

    idClient  |
------------- |
999999999999  |
888888888888  |

  *DeletePeriodicJobs table :*

    idClient  | idJob |
------------- | :---: |
999999999999  | 1     |
888888888888  | 2     |

  *InsertJobs table :*

    idClient  | idJob   | parameters           | periodic | time_periodic |
------------- | :-----: | :------------------: | :------: | :-----------: |
999999999999  | 1       | -oX - www.google.com | 1        | 30            |
888888888888  | 2       | -oX - www.in.gr      | 0        | 0             |

##### Αρχεία
  - Αρχείο `DbConnector` : Περιέχει όλες τις μεθόδους που αναλαμβάνουν την επικοινωνία με την βάση. Ο constructor ελέγχει αν υπάρχει η βάση μέσω του `DbCreation` constructor. Πριν καλεστεί κάποια μέθοδος πρέπει να χρησιμοποιηθεί η μέθοδος `open()` και στο τέλος η `close()`, η πρώτη ανοίγει και διαβάζει και η δεύτερη κλείνει την βάση που χρησιμοποιεί το πρόγραμμα αντίστοιχα.
  - Αρχείο `DbCreation` : Δημιουργείται από τον constructor του `DbConnector`. Εάν η βάση δεδομένων δεν υπάρχει, δημιουργείται. Η βάση αποτελείται απο τέσσερα tables:
    - PendingClients, έχει δύο πεδία `idClient VARCHAR` και `accept INT`. Κάθε εγγραφή στο table δηλώνει μια ενέργεια του χρήστη.
    - DeletePeriodicJobs, έχει δύο πεδία `idClient VARCHAR` και `idJob INT`. Κάθε εγγραφή στο table δηλώνει μια ενέργεια του χρήστη. 
    - InsertJobs, έχει πέντε πεδία `idClient VARCHAR`,`idJob INT`,`parameters VARCHAR`,`periodic INT`,`time_periodic INT`. Κάθε εγγραφή στο table δηλώνει μια ενέργεια του χρήστη.
    - TurnOffClients, έχει ένα πεδίο `idClient VARCHAR`. Προφανώς κάθε εγγραφή αφορά κάποιον client που πρέπει να τερματιστεί.
  - Αρχείο `DbObject` : Είναι το αντικείμενο που επιστρέφουν τα ερωτήματα στην βάση. Για την ακρίβεια τα ερωτήματα-μέθοδοι από το αρχείο `DbConnector` της μορφής `Database_Get_*` επιστρέφουν `ArrayList<DbObject>`. Έχει τέσσερα πεδία :
    - `String Client_id` : Το ID του Client.
    - `int job_id` : To ID της εντολής nmap.
    - `int accept` : Το accept δείχνει εάν ο χρήστης αποδέχτηκε ή απέρριψε κάποιον Client όσο η εφαρμογή βρισκόταν σε κατάσταση χωρίς connection.
    - `Job job` : To αντικείμενο Job κρατά τις πληροφορίες που χρειάζονται ώστε οταν ο χρήστης στείλει δουλειές σε Clients, σε κατάσταση χωρίς connection, μόλις αποκατασταθεί το πρόβλημα της σύνδεσης θα σταλθούν οι δουλειές κατευθείαν (διαφορετικά θα κρατούσαμε μόνο το id τoυ εκάστοτε job και θα ρωτούσαμε στην cache σε ποιό job αντιστοιχεί για να πάρουμε τα πεδία που χρειαζόμαστε )

#### fragments
  - Αρχείο `ClientResultsFragment` :
  - Αρχείο `ConfigureIpFragment` :
  - Αρχείο `DeletePeriodicJobFragment` :
  - Αρχείο `InsertJobsFragment` :
  - Αρχείο `StatusFragment` :
  - Αρχείο `TotalResultsFragment` :
  - Αρχείο `TurnOffClientFragment` :
  - Αρχείο `TypeNewJobFragment` :

#### server communication
  - Αρχείο `DatabaseInteraction` :
  - Αρχείο `JsonParsers` :
  - Αρχείο `LogginListener` :
  - Αρχείο `ServerInteraction` :
  - Αρχείο `updateListener` :
