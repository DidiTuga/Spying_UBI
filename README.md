# Spying @ UBI: Um spyfall à maneira!

## Feito
Feito tudo, só não meti um botão para mudar de lingua(PT|EN).

## Enunciado 
O objetivo deste trabalho é implementar um jogo de festa que pode ser jogado por quatro
ou mais jogadores. Neste jogo há dois tipos de jogadores que se confrontam: espiões e
investigadores. No início de uma ronda cada investigador recebe uma localização (vamos
considerar locais emblemáticos da UBI: cantina, bar da VI fase, anfiteatro 6.01, biblioteca,
etc) ao passo que cada espião recebe a indicação de que é esse o seu papel e desconhece
o local onde os demais se encontram. Os jogadores (seguindo a ordem sorteada pela
aplicação) fazem uma pergunta de resposta sim|não sobre esse local (e.g. “Apreciaste o
tempo que lá passaste na última vez?”) a outro jogador da sua escolha. Os investigadores
utilizam estas perguntas para tentar descobrir espiões (respostas falsas ou suspeitas) e os
espiões devem utiliza-las para descobrir o local (em conjunto com as pistas recolhidas das
demais perguntas) sem dar conta da sua ignorância aos demais. Em qualquer momento
da ronda um jogador (investigador ou espião!) pode, na sua vez de jogar, acusar outro
de ser espião. Se a maioria dos jogadores concordar com a acusação o acusado tem de
revelar a sua identidade (investigador ou espião) e é eliminado. Em qualquer momento
da ronda um espião pode tentar adivinhar o local - se acertar é declarado vencedor, caso
contrário é eliminado. O jogo termina quando todos os espiões forem descobertos ou
quando o local for revelado por um espião ou quando restarem apenas 2 jogadores.
A versão básica da aplicação deve aceitar os nomes de todos os utilizadores, sortear a
sua vez de jogar e o papel que lhe é atribuído, sortear um local e dar início ao jogo. A mecânica assenta na passagem de telemóvel de mão (desinfetada!) em mão (desinfetada!)
revelando apenas a informação que cada um tem de conhecer.
A versão mais elaborada da aplicação deve mostrar uma descrição muito apelativa para
cada local (a pensar nas pessoas que nunca vieram à UBI: texto com curiosidades, imagens, etc.). Considere a possibilidade de alargar o jogo para outras línguas como forma de consolidar conhecimentos.
