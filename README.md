
<p align="center">
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/University_of_Prishtina_logo.svg/1200px-University_of_Prishtina_logo.svg.png" alt="University of Prishtina Logo" width="200">
</p>


# Zhvillimi i Sistemit Interaktiv për Menaxhimin e Regjistrimit të Studentëve në FIEK

## Përmbledhje

Ky projekt, i zhvilluar si pjesë e një detyre universitare në Fakultetin e Inxhinierisë Elektrike dhe Kompjuterike (FIEK), është një sistem interaktiv për menaxhimin e regjistrimit të studentëve. Ai ofron një ndërfaqe miqësore për përdoruesit, si për studentët ashtu edhe për profesorët, për të trajtuar regjistrimin, menaxhimin e kurseve, notat dhe njoftimet. Sistemi është ndërtuar duke përdorur Java, me ndërfaqen grafike të dizajnuar përmes Scene Builder dhe FXML, dhe përfshin një bazë të dhënave për ruajtjen dhe menaxhimin e të dhënave.

### Karakteristikat Kryesore
- **Funksionalitetet për Studentët**:
  - Hyrja dhe menaxhimi i profilit, përfshirë përditësimin e fotos së profilit.
  - Regjistrimi dhe menaxhimi i kurseve.
  - Shfaqja e notave, dorëzimeve dhe njoftimeve.
  - Faqja kryesore interaktive me një orë të personalizuar dhe navigim anësor.

- **Funksionalitetet për Profesorët**:
  - Menaxhimi i klasave, kurseve dhe notave.
  - Shqyrtimi dhe menaxhimi i dorëzimeve të studentëve.
  - Marrja e njoftimeve dhe menaxhimi i profilit të tyre.

- **Karakteristikat e Përgjithshme**:
  - Sistem i sigurt hyrjeje me hash të fjalëkalimeve.
  - Integrim me bazën e të dhënave për ruajtje të qëndrueshme të të dhënave.
  - Paginim për shfaqje efikase të të dhënave.
  - Ndërfaqe e përgjegjshme me stilizim CSS.

## Struktura e Projektit

- **`src/`**: Drejtoria kryesore e kodit burimor.
  - `MainApp.java`: Pika e hyrjes së aplikacionit.
  - `DB_Connector.java`: Trajton lidhjet me bazën e të dhënave.
  - `SceneManager.java` dhe `SceneLocator.java`: Menaxhon tranzicionet e skenave në ndërfaqen grafike.
  - `LogInService.java`: Menaxhon logjikën e hyrjes për studentët dhe profesorët.
  - Modelet, DTO-të, Repositories dhe Services: Trajtojnë menaxhimin e të dhënave dhe logjikën e biznesit.
- **`SQL/`**: Përmban skedarin `tables.sql` për konfigurimin e skemës së bazës së të dhënave.

## Instalimi

1. **Klono Repozitorin**:
   ```
   git clone https://github.com/[YourUsername]/Zhvillimi-i-Sistemit-Interaktiv-per-menaxhimin-e-regjistrimit-te-studenteve-ne-FIEK.git
   ```

2. **Konfiguro Bazën e të Dhënave**:
- Importo skedarin `SQL/tables.sql` në bazën tënde të të dhënave SQL për të krijuar tabelat e nevojshme (Users, Students, Professors, Courses, Enrollments, Grades, Submissions, Announcements, Assignments, Classes).
- Konfiguro lidhjen me bazën e të dhënave në `DB_Connector.java` me kredencialet e tua.

3. **Ekzekuto Aplikacionin**:
- Hap projektin në IDE-në tënde të preferuar të Java-s (p.sh., IntelliJ IDEA, Eclipse).
- Ndërto dhe ekzekuto `MainApp.java`.

## Kontribuesit
- Bashkim Hashani
- Benjamin Voca
- Diellart Mulolli
- Erblin Syla
- Endri Sojeva
- Fatos Rama

## Licenca
Ky projekt është i licencuar nën [Licencën MIT](LICENSE).

