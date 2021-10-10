# Para poder usar la función print e imprimir sin saltos de línea
from __future__ import print_function

import json # Para poder trabajar con objetos JSON
import pprint # Para poder hacer uso de PrettyPrinter
import sys # Para poder usar exit

from elasticsearch import Elasticsearch
from elasticsearch import helpers
import json
import io

def main():

    # Nos conectamos por defecto a localhost:9200
    global es
    es = Elasticsearch()

    # Obtenemos automáticamente los documentos relacionados
    results = es.search(
        index="ejercicio1",
        body = {
            "query": {
               "match": {
                    "selftext": {
                        "query": "stress"
                    }
                }
            },
            "size": 15
        }
    )

    ids = []

    # Iteramos sobre los resultados, no es preciso preocuparse de las
    # conexiones consecutivas que hay que hacer con el servidor ES
    for i in range(0, len(results["hits"]["hits"])):
        ids.append({
            "_id": results["hits"]["hits"][i]["_id"]
        })

    # Inicializamos consulta MLT a la que pasamos posteriormente los documentos
    consulta = {
        "query": {
            "more_like_this": {
                "fields": [
                    "title",
                    "selftext"
                ],
                "like": [],
                "min_term_freq": 1,
                "max_query_terms": 10
            }
        },
        "size": 15
    }

    # Recorremos los identificadores para insertarlos en la consulta
    for id in ids:
        consulta["query"]["more_like_this"]["like"].append({
            "_index": "ejercicio1",
            "_id": id["_id"]
        })

    # Realizamos la búsqueda
    results = es.search(
        index = "ejercicio1",
        body = consulta
    )

    # Obtenemos los resultados, que volcaremos a un JSON
    posts = results["hits"]["hits"]

    datos = {}
    datos["posts"] = []

    # Iteramos sobre los resultados, no es preciso preocuparse de las
    # conexiones consecutivas que hay que hacer con el servidor ES
    for hit in posts:
        datos["posts"].append({
            "author": hit["_source"]["author"],
            "created_utc": hit["_source"]["created_utc"],
            "selftext": hit["_source"]["selftext"]
        })

    # Guardamos los documentos más relevantes en un fichero JSON
    with open("ejercicio2.json","w",encoding="utf8") as fichero:
        json.dump(datos, fichero, indent=4, ensure_ascii=False)

if __name__ == '__main__':
    main()