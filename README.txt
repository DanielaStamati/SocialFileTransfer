Stamati Daniela 341C5
Chelcioiu Ionut Daniel	341C5


																						Tema 2 
																													
						Arhiva contine atat sursele (codul) cat si o structura de fisiere necesare rularii temei
					In cadrul acestei teme am reusit sa implementam atat partea de server cat si partea
					de client. Aceste parti au fost integrate in proiectul inceput la tema 1. De asemenea am 
					adaugat partea de logging. 
					
					Exemplu de rulare : 
							
							Programul primeste in linia de comanda un unsername -> userul ce lanseaza aplicatia.
						Pentru a putea rula cu succes aplicatie sunt necesare niste fisiere de unde aplicatia isi ia
						datele initiale. Spre exemplu daca se ruleaza aplicatia cu usernameul deirdre, va fi necesar
						un fisier deirdre.txt in care se afla pe prima linie ip si portul urmat de specificarea unui/mai multi
						useri si fisierele asociate. Exemplu
								127.0.0.1 30000 - ip si port user care lanseaza aplicatia
								duffy 127.0.0.1 30000 3 - numele unui user ip, port si cate fisiere detine 
								dfile1	-
								dfile2  -  fisierele pe care le detine 
								dfile3  -
					
					Functionalitate: 
						
							La pornirea aplicatiei se incarca datele initiale. Se pot observa userii incarti in partea din dreapta.
						Atunci cand se da un click pe unul din useri se va incarca lista de fisiere detinute de userul respectiv.
						La dublu-click pe oricare din fisierele detinute de un user se va incepe transferul de fisier de la clientul
						respectiv. Momentan folderele sunt plasate pe acelasi nivel cu src. 
							Serverul primeste conexiuni multiple adica mai multi useri pot initia mai multe treanferuri de fisiere in 
						acelasi timp. 
							Aplicatia poate loga diferite mesaje atat pe partea de client cat si pe partea de server, acestea fiind 
						puse intr-un fisier filelog.log	
							Transferul de date se realizeaza in mod asincron, utilizand metodele de read si write ale clasei SocketChannel. 
					
					
					
					
						
								
								