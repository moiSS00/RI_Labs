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

	# Se pide entrada al usuario
    print("Selecciona la métrica a utilizar:")
    print("1    Google normalized distance")
    print("2    JLH score")
    print("3    Mutual information")
    print("4    Chi square")

    entrada = input("Número de métrica: ")
    metrica = 1

	# Comprobación de la entrada
    try:
        metrica = int(entrada)
    except:
        print("El valor introducido no es válido. Debes seleccionar un valor entre 1 y 4 inclusive.")
        return

    if (metrica == 1):
        #Obtenemos autmáticamente los términos más significativos según la métrica usada
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
                "aggs": {
                    "Terminos más significativos": {
                        "significant_terms": {
                            "field": "selftext",
                            "size": 10,
                            "gnd": {}
                        }
                    }
                }
            }
        )
    elif (metrica == 2):
        #Obtenemos autmáticamente los términos más significativos según la métrica usada
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
                "aggs": {
                    "Terminos más significativos": {
                        "significant_terms": {
                            "field": "selftext",
                            "size": 10,
                            "jlh": {}
                        }
                    }
                }
            }
        )
    elif (metrica == 3):
        #Obtenemos autmáticamente los términos más significativos según la métrica usada
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
                "aggs": {
                    "Terminos más significativos": {
                        "significant_terms": {
                            "field": "selftext",
                            "size": 10,
                            "mutual_information": {
                                "include_negatives": "false"
                            }
                        }
                    }
                }
            }
        )
    elif (metrica == 4):
        #Obtenemos autmáticamente los términos más significativos según la métrica usada
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
                "aggs": {
                    "Terminos más significativos": {
                        "significant_terms": {
                            "field": "selftext",
                            "size": 10,
                            "chi_square": {}
                        }
                    }
                }
            }
        )
    else:
        print("El valor introducido no es válido. Debes seleccionar un valor entre 1 y 4 inclusive.")
        return

    # Recogemos los resultados de la agregación que nos interesa
    aggregations = results["aggregations"]["Terminos más significativos"]["buckets"]
    palabras = ""

    # Formamos una lista con los términos encontrados separados por un espacio
    for i in range(0,len(aggregations)):
        palabras += " " + aggregations[i]["key"]

    # Las imprimimos para verificar el correcto funcionamiento del script
    print(palabras)

    # Lanzamos la búsqueda que dejara en un fichero como salida los resultados obtenidos
    results = es.search(
        index="ejercicio1",
        body={
            "query": {
                "match": {
                    "selftext":{
                        "query": palabras,
                        "operator": "or"
                    }
                }
            },
            "size": 15
        }
    )

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
    with open("ejercicio1.json","w",encoding="utf8") as fichero:
        json.dump(datos, fichero, indent=4, ensure_ascii=False)

if __name__ == '__main__':
    main()