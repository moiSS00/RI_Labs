#-------------------------------------------------------------------------------
# Name:        ejercicio4
# Purpose:
#
# Author:      Samuel Rodríguez Ares y Moisés Sanjurjo Sánchez
#
# Created:     12/12/2020
# Copyright:
# Licence:
#-------------------------------------------------------------------------------

from elasticsearch import Elasticsearch

import json

def main():

    # Nos conectamos a nuestro ElasticSearch
    global es
    es = Elasticsearch()

    # Se pide la entrada de la que se quiere obtener factores comórbidos relativos
    print("Seleccione el transtorno del que quiere obtener factores comórbidos relativos:")
    print("1    Ideación suicida")
    print("2    Conductas autolesivas")

    entrada = input("Opción: ")
    opcion = 1

    # Validación de entrada
    try:
        opcion = int(entrada)
    except:
        print("El valor introducido no es válido. Debes seleccionar un valor entre 1 y 2 inclusive.")
        return

    # Definimos la consulta a realizar en función de la opción escogida
    palabras = []
    if (opcion == 1):
        palabras = ["suicide", "suicidal", "kill myself", "killing myself", "end my life"]
    elif (opcion == 2):
        palabras = ["self harm"]
    else:
        print("El valor introducido no es válido. Debes seleccionar un valor entre 1 y 2 inclusive.")
        return

    # Formación de parámetro query a partir del array de palabras
    consulta = ""
    for i in range(0, len(palabras)):
        consulta += "(" + palabras[i] + ")"
        if (len(palabras) != 1 and i < len(palabras) - 1):
            consulta += " OR "

    print(consulta)

    # Búsqueda del subconjunto de posibles factores comórbidos
    busqueda = es.search(
        index = "ejercicio4",
        body = {
            "query": {
                "query_string": {
                    "fields": [
                        "selftext",
                        "title",
                        "author",
                        "subreddit"
                    ],
                    "query": consulta
                }
            },
            "aggs": {
                "terms_suicida_title": {
                    "significant_terms": {
                        "field": "title",
                        "size": 500,
                        "gnd": {}
                    }
                },
                "terms_suicida_author": {
                    "significant_terms": {
                        "field": "author",
                        "size": 500,
                        "gnd": {}
                    }
                },
                "terms_suicida_selftext": {
                    "significant_terms": {
                        "field": "selftext",
                        "size": 500,
                        "gnd": {}
                    }
                },
                "terms_suicida_subreddit": {
                    "significant_terms": {
                        "field": "subreddit",
                        "size": 500,
                        "gnd": {}
                    }
                }
            }
        }
    )

    # Recogemos los términos significativos obtenidos
    terminos = []
    for agg in busqueda["aggregations"]:
        for term in busqueda["aggregations"][agg]["buckets"]:
            if term not in palabras:
                terminos.append(term["key"])

    titulos = []
    fichero = ""
    if (opcion == 1):
        fichero = "SuicideComorbidity.json"
    elif (opcion == 2):
        fichero = "SelfHarmComorbidity.json"

    # Cargamos la lista de títulos del JSON correspondiente
    with open(fichero, encoding = "utf-8-sig") as f:
        datos = json.load(f)
        for d in datos:
            titulos.append(d["title"])

    # Nos quedamos con aquellos términos que estén en la lista correspondiente
    salida = []
    for term in terminos:
        for titulo in titulos:
            if term in titulo.split() and term not in salida:
                print(term)
                salida.append(term)

    # Guardamos los resultados en un fichero TXT
    with open("salida.txt", "wb") as f:
        for term in salida:
            f.write(term.encode("UTF-8") + "\n".encode("UTF-8"))

if __name__ == '__main__':
    main()
