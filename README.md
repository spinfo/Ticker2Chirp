# Ticker2Chirp
Ticker2Chirp ist eine Toolbox, mit der sich Ticker zu Fußball-Spielen im XML-Formal in neu terminierte Ticker im cvs-Input-Format von autoChirp transformieren lassen. 
Ticker2Chirp sucht dabei automatisch neu angesetzte Begegnungen im vorhandenen Korpus der historischen Paarungen. Sie wird eingesetzt beim Projekt twitter.com/retrolivetext. Auskunft über dieses Projekt geben die beiden Blogposts https://texperimentales.hypotheses.org/2596 (von Jürgen Hermes) und 
http://fussballlinguistik.de/2018/06/goals-from-the-past/ (von Simon Meier).

## Referenzierte Software
Die Daten stammen ursprünglich vom Portal www.weltfussball.de. Sie sind Teil der Fußball-bezogenen Korpora des Blogprojekts www.fussballlinguistik.de. Simon Meier, der diesen Blog betreibt, hat unter github.com/fussballlinguist/livetext ein Skript veröffentlicht, mit dem die Daten in ein spezifiziertes XML-Format überführt werden, welches als Input-Format für Ticker2Chirp funktioniert.
Das Output-Format von Ticker2Chirp kann wiederum direkt beim Webservice https://autochirp.spinfo.uni-koeln.de importiert werden, wo dafür gesorgt wird, dass die Daten zu den richtigen Zeitpunkten auch getwittert werden. autoChirp ist ein Webservice, der vom Institut für Digital Humanities der Uni Köln entwickelt wurde.
