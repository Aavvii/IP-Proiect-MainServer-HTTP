DROP TABLE utilizator CASCADE CONSTRAINTS;
/
DROP TABLE carte CASCADE CONSTRAINTS;
/
DROP TABLE istoric CASCADE CONSTRAINTS;
/
DROP TABLE recenzii CASCADE CONSTRAINTS;
/

create table utilizator(
   id int NOT NULL PRIMARY KEY,
   nume VARCHAR2(100) NOT NULL,
   prenume VARCHAR2(100) NOT NULL,
   nickname varchar2(200) unique,
   parola varchar2(100),
   email varchar2(100)
   );
/                        
create table carte(
   id int not null primary key,
   titlu varchar(100),
   autor varchar2(100),
   gen varchar2(100),
   nr_pag int,
   isbn varchar2(100) unique,
   rating numeric(3,2),
   rezumat varchar2(100)
   );
/               
create table istoric(
  id_utilizator int,
  id_carte int,
  data_vizitare date,
  constraint fk_carte foreign key(id_carte) references carte(id),
  constraint fk_utiliztator foreign key (id_utilizator) references utilizator(id)
  );
/
create table recenzii(
  id int not null primary key,
  id_carte int,
  rating numeric(3,2),
  review varchar2(600),
  constraint fk_crt foreign key (id_carte) references carte(id)
  );
 /       
 
select * from carte;
select * from utilizator;
select * from recenzii;
select * from istoric;


SET SERVEROUTPUT ON;
DECLARE

TYPE varr IS VARRAY(1000) OF varchar2(255);
  lista_nume varr := varr('Ababei','Acasandrei','Adascalitei','Afanasie','Agafitei','Agape','Aioanei','Alexandrescu','Alexandru','Alexe','Alexii','Amarghioalei','Ambroci','Andonesei','Andrei','Andrian','Andrici','Andronic','Andros','Anghelina','Anita','Antochi','Antonie','Apetrei','Apostol','Arhip','Arhire','Arteni','Arvinte','Asaftei','Asofiei','Aungurenci','Avadanei','Avram','Babei','Baciu','Baetu','Balan','Balica','Banu','Barbieru','Barzu','Bazgan','Bejan','Bejenaru','Belcescu','Belciuganu','Benchea','Bilan','Birsanu','Bivol','Bizu','Boca','Bodnar','Boistean','Borcan','Bordeianu','Botezatu','Bradea','Braescu','Budaca','Bulai','Bulbuc-aioanei','Burlacu','Burloiu','Bursuc','Butacu','Bute','Buza','Calancea','Calinescu','Capusneanu','Caraiman','Carbune','Carp','Catana','Catiru','Catonoiu','Cazacu','Cazamir','Cebere','Cehan','Cernescu','Chelaru','Chelmu','Chelmus','Chibici','Chicos','Chilaboc','Chile','Chiriac','Chirila','Chistol','Chitic','Chmilevski','Cimpoesu','Ciobanu','Ciobotaru','Ciocoiu','Ciofu','Ciornei','Citea','Ciucanu','Clatinici','Clim','Cobuz','Coca','Cojocariu','Cojocaru','Condurache','Corciu','Corduneanu','Corfu','Corneanu','Corodescu','Coseru','Cosnita','Costan','Covatariu','Cozma','Cozmiuc','Craciunas','Crainiceanu','Creanga','Cretu','Cristea','Crucerescu','Cumpata','Curca','Cusmuliuc','Damian','Damoc','Daneliuc','Daniel','Danila','Darie','Dascalescu','Dascalu','Diaconu','Dima','Dimache','Dinu','Dobos','Dochitei','Dochitoiu','Dodan','Dogaru','Domnaru','Dorneanu','Dragan','Dragoman','Dragomir','Dragomirescu','Duceac','Dudau','Durnea','Edu','Eduard','Eusebiu','Fedeles','Ferestraoaru','Filibiu','Filimon','Filip','Florescu','Folvaiter','Frumosu','Frunza','Galatanu','Gavrilita','Gavriliuc','Gavrilovici','Gherase','Gherca','Ghergu','Gherman','Ghibirdic','Giosanu','Gitlan','Giurgila','Glodeanu','Goldan','Gorgan','Grama','Grigore','Grigoriu','Grosu','Grozavu','Gurau','Haba','Harabula','Hardon','Harpa','Herdes','Herscovici','Hociung','Hodoreanu','Hostiuc','Huma','Hutanu','Huzum','Iacob','Iacobuta','Iancu','Ichim','Iftimesei','Ilie','Insuratelu','Ionesei','Ionesi','Ionita','Iordache','Iordache-tiroiu','Iordan','Iosub','Iovu','Irimia','Ivascu','Jecu','Jitariuc','Jitca','Joldescu','Juravle','Larion','Lates','Latu','Lazar','Leleu','Leon','Leonte','Leuciuc','Leustean','Luca','Lucaci','Lucasi','Luncasu','Lungeanu','Lungu','Lupascu','Lupu','Macariu','Macoveschi','Maftei','Maganu','Mangalagiu','Manolache','Manole','Marcu','Marinov','Martinas','Marton','Mataca','Matcovici','Matei','Maties','Matrana','Maxim','Mazareanu','Mazilu','Mazur','Melniciuc-puica','Micu','Mihaela','Mihai','Mihaila','Mihailescu','Mihalachi','Mihalcea','Mihociu','Milut','Minea','Minghel','Minuti','Miron','Mitan','Moisa','Moniry-abyaneh','Morarescu','Morosanu','Moscu','Motrescu','Motroi','Munteanu','Murarasu','Musca','Mutescu','Nastaca','Nechita','Neghina','Negrus','Negruser','Negrutu','Nemtoc','Netedu','Nica','Nicu','Oana','Olanuta','Olarasu','Olariu','Olaru','Onu','Opariuc','Oprea','Ostafe','Otrocol','Palihovici','Pantiru','Pantiruc','Paparuz','Pascaru','Patachi','Patras','Patriche','Perciun','Perju','Petcu','Pila','Pintilie','Piriu','Platon','Plugariu','Podaru','Poenariu','Pojar','Popa','Popescu','Popovici','Poputoaia','Postolache','Predoaia','Prisecaru','Procop','Prodan','Puiu','Purice','Rachieru','Razvan','Reut','Riscanu','Riza','Robu','Roman','Romanescu','Romaniuc','Rosca','Rusu','Samson','Sandu','Sandulache','Sava','Savescu','Stavarache','Stefan','Stefanita','Stingaciu','Stiufliuc','Stoian','Stoica','Stoleru','Stolniceanu','Stolnicu','Strainu','Strimtu','Suhani','Tabusca','Talif','Tanasa','Teclici','Teodorescu','Tesu','Tifrea','Timofte','Tincu','Tirpescu','Toader','Tofan','Toma','Toncu');
  lista_prenume varr := varr('Adina','Alexandra','Alina','Ana','Anca','Anda','Andra','Andreea','Andreia','Antonia','Bianca','Camelia','Claudia','Codrina','Cristina','Daniela','Daria','Delia','Denisa','Diana','Ecaterina','Elena','Eleonora','Elisa','Ema','Emanuela','Emma','Gabriela','Georgiana','Ileana','Ilona','Ioana','Iolanda','Irina','Iulia','Iuliana','Larisa','Laura','Loredana','Madalina','Malina','Manuela','Maria','Mihaela','Mirela','Monica','Oana','Paula','Petruta','Raluca','Sabina','Sanziana','Simina','Simona','Stefana','Stefania','Tamara','Teodora','Theodora','Vasilica','Xena','Adrian','Alex','Alexandru','Alin','Andreas','Andrei','Aurelian','Beniamin','Bogdan','Camil','Catalin','Cezar','Ciprian','Claudiu','Codrin','Constantin','Corneliu','Cosmin','Costel','Cristian','Damian','Dan','Daniel','Danut','Darius','Denise','Dimitrie','Dorian','Dorin','Dragos','Dumitru','Eduard','Elvis','Emil','Ervin','Eugen','Eusebiu','Fabian','Filip','Florian','Florin','Gabriel','George','Gheorghe','Giani','Giulio','Iaroslav','Ilie','Ioan','Ion','Ionel','Ionut','Iosif','Irinel','Iulian','Iustin','Laurentiu','Liviu','Lucian','Marian','Marius','Matei','Mihai','Mihail','Nicolae','Nicu','Nicusor','Octavian','Ovidiu','Paul','Petru','Petrut','Radu','Rares','Razvan','Richard','Robert','Roland','Rolland','Romanescu','Sabin','Samuel','Sebastian','Sergiu','Silviu','Stefan','Teodor','Teofil','Theodor','Tudor','Vadim','Valentin','Valeriu','Vasile','Victor','Vlad','Vladimir','Vladut');
  lista_nume_autor varr := varr('William Shakespeare','Agatha Christie', 'Barbara Cartland', 'Danielle Steel', 'Harold Robbins', 'Harold Robbins', 'Enid Blyton', 'Sidney Sheldon', 'J. K. Rowling', 'Gilbert Patten', 'Dr. Seuss', 'Eiichiro Oda', 'Leo Tolstoy', 'Corín Tellado', 'Jackie Collins', 'Horatio Alger', 'R. L. Stine', 'Dean Koontz' , 'Dean Koontz', 'Alexander Pushkin', 'Stephen King', 'Paulo Coelho','Rodica Ojog-Bra?oveanu','Gheorghe Buzoianu','Constantin Chiri??','Theodor Constantin','Denis Grigorescu','Bogdan Hrib','Florin Andrei Ionescu','?erban M?rgineanu','Oana Stoica Mujea','Leonida Neamtu','Jesy Pior','Monica Ramirez');
  lista_genuri_carti varr := varr('Fictiune','Carti pentru copii','Biografii si memorii','Istorie','Business, economie, finante','Psihologie, Pedagogie','Parenting si familie','Self Help','Spiritualitate, ezoterism','Diete si fitness','Hobby, timp liber','Gastronomie','Ghiduri de calatorie, harti','Filologie, Filosofie','Sociologie, stiinte politice','Religie','Stiinte','Arta, arhitectura si fotografie','Dictionare si Enciclopedii','Limbi straine');
  lista_titluri_carti  varr := varr('MAITREYI','O MIE NOU? SUTE OPTZECI ?I PATRU','B?TRÂNUL ?I MAREA','PORTRETUL LUI DORIAN GRAY','DON QUIJOTE DE LA MANCHA','FERMA ANIMALELOR','DE CE IUBIM FEMEILE?','LOLITA','FEMEIA LA 30 DE ANI','DRAGOSTEA ÎN VREMEA HOLEREI','MICUL PRIN?','PUTEREA PREZENTULUI','INTELIGEN?A EMO?IONAL?','IDIOTUL','PE CULMILE DISPER?RII','LA PARADISUL FEMEILOR','LA PARADISUL FEMEILOR','STR?INUL','R?T?CIRILE FETEI NES?BUITE','PARFUMUL','ALCHIMISTUL','ADAM ?I EVA','LEAG?NUL RESPIRA?IEI','O CIUD??ENIE A MIN?II MELE','AMINTIRI DIN PRIBEGIE','ZBOR DEASUPRA UNUI CUIB DE CUCI','ZECE NEGRI MITITEI','S? UCIZI O PAS?RE CÎNT?TOARE','LIBERTATEA – CURAJUL DE A FI TU ÎNSU?I','ARTA DE A AVEA ÎNTOTDEAUNA DREPTATE');

  v_nume VARCHAR2(100);
  v_prenume VARCHAR2(100);
  v_numar  number(4);
  v_nickname VARCHAR2(200);
  v_email varchar2(100);
  v_parola varchar(100);
  
  v_titlu_carte   VARCHAR2(100);
  v_nume_autor VARCHAR2(100);
  v_gen_carte VARCHAR2(100);
  v_nr_pagini number(4);
  v_isbn  varchar2(100);
  v_carte_rating numeric(3,2);
  v_carte_rezumat VARCHAR2(100);

  v_numar_utilizatori int;
  v_numar_carti int;
  v_id_utilizator int;
  v_id_carte int;
  
  v_review varchar2(600);
BEGIN
-- populare utilizator 
   FOR v_id IN 1..400 LOOP
        v_nume := lista_nume(TRUNC(DBMS_RANDOM.VALUE(0,lista_nume.count))+1);
        v_prenume :=lista_prenume(TRUNC(DBMS_RANDOM.VALUE(0,lista_prenume.count))+1);
        v_numar :=TRUNC(DBMS_RANDOM.VALUE(0,1000))+1;
        v_nickname := v_prenume||v_nume||v_numar;
        v_email := lower(v_prenume ||'.'|| v_nume);
        v_parola := FLOOR(DBMS_RANDOM.VALUE(100,999)) || CHR(FLOOR(DBMS_RANDOM.VALUE(65,91))) || CHR(FLOOR(DBMS_RANDOM.VALUE(65,91))) || FLOOR(DBMS_RANDOM.VALUE(0,9));
        insert into utilizator values (v_id, v_nume, v_prenume, v_nickname, v_parola, v_email);
    end loop;
    
    --populare carte
   for v_id in 1..lista_titluri_carti.count loop
      v_titlu_carte := lista_titluri_carti(v_id);
      v_nume_autor := lista_nume_autor(TRUNC(DBMS_RANDOM.VALUE(0,lista_nume_autor.count))+1);
      v_gen_carte := lista_genuri_carti(TRUNC(DBMS_RANDOM.VALUE(0,lista_genuri_carti.count))+1);
      v_nr_pagini :=TRUNC(DBMS_RANDOM.VALUE(0,800))+1;
      v_isbn := FLOOR(DBMS_RANDOM.VALUE(100,999))||'-'||FLOOR(DBMS_RANDOM.VALUE(300,999))||'-'||FLOOR(DBMS_RANDOM.VALUE(100,999))||'-'||FLOOR(DBMS_RANDOM.VALUE(100,999))||'-'||FLOOR(DBMS_RANDOM.VALUE(0,9));
      v_carte_rating :=round(DBMS_RANDOM.VALUE(2,10),2);
      v_carte_rezumat :='insert here text later';
      insert into carte values (v_id, v_titlu_carte, v_nume_autor, v_gen_carte, v_nr_pagini, v_isbn, v_carte_rating, v_carte_rezumat);
    end loop;  
    
    --populare istoric
    select count(*) into v_numar_utilizatori from utilizator;
    select count(*) into v_numar_carti from carte;
    for i in 1..1000 loop
        v_id_utilizator :=TRUNC(DBMS_RANDOM.VALUE(0,v_numar_utilizatori))+1;
        v_id_carte := TRUNC(DBMS_RANDOM.VALUE(0,v_numar_carti))+1;
        insert into istoric values ( v_id_utilizator, v_id_carte, sysdate);
   end loop;
    
    -- populare recenzii
    for v_id in 1..500 loop
       v_id_carte := TRUNC(DBMS_RANDOM.VALUE(0,v_numar_carti))+1;
       select rating into v_carte_rating from carte where id=v_id_carte;
       v_review:='O alta carte intriganta..poate buna, poate rea.';
       insert into recenzii values (v_id, v_id_carte, v_carte_rating, v_review);
    end loop;
    
END;
/
select * from utilizator;
select * from carte;
select * from istoric;
select * from recenzii;