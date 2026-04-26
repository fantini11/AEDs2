#include <stdio.h>

typedef struct Data{
	int ano;
	int mes;
	int dia;
}Data;

Data parse_data(char *s){
	Data d;
	sscanf(s,"%d-%d-%d", &d.ano, &d.mes, &d.dia);
	return d;
}

void formatar_data(char *buffer, Data *d){
	sprintf(buffer, "%02d/%02d/%04d", d->dia, d->mes, d->ano);
}

typedef struct Hora{
	int hora;
	int minuto;
}Hora;

int main(){
	Data d = parse_data("2026-04-07");
	char buffer[11];
	formatar_data(buffer, &d);
	printf("%s", buffer);
}
