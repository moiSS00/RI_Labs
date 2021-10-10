#-------------------------------------------------------------------------------
# Name:        ejercicio3
# Purpose:
#
# Author:      Samuel Rodríguez Ares y Moisés Sanjurjo Sánchez
#
# Created:     08/12/2020
# Copyright:
# Licence:
#-------------------------------------------------------------------------------

from elasticsearch import Elasticsearch

import urllib.request
import json

baseURL = "https://www.wikidata.org/w/api.php?action="

def main():

    # Nos conectamos a Elasticsearch
    global es
    es = Elasticsearch()

    # Realizamos la búsqueda de términos relacionados
    results = es.search(
            index="ejercicio1",
            body = {
                "query": {
                   "match": {
                        "selftext": {
                            "query": "medicine medication medicament drug",
                            "operator": "or"
                        }
                    }
                },
                "aggs": {
                    "Terminos más significativos": {
                        "significant_terms": {
                            "field": "selftext",
                            "size": 3000,
                            "gnd": {}
                        }
                    }
                }
            }
    )

    # Recogemos los resultados de la agregación que nos interesa
    aggregations = results["aggregations"]["Terminos más significativos"]["buckets"]
    terminos = []

    #Formamos una lista con los términos encontrados separados por un espacio
    for i in range(0,len(aggregations)):
        terminos.append(aggregations[i]["key"])

    medicamentos = set([])

    # Para cada palabra, se obtienen sus ID de búsqueda en Wikidata
    # con el objetivo de validar que realmente sean medicamentos
    for term in terminos:
        jsonIds = freeTextSearch(term)
        ids = []

        for entity in jsonIds:
            ids.append(entity["id"])

        ids = "|".join(ids)

        urlProps = baseURL + "wbgetentities&ids="+ urllib.parse.quote_plus(ids) + "&languages=en&format=json"
        data = urllib.request.urlopen(urlProps).read()
        data = json.loads(data)

        for id in ids.split("|"):
            try:
                if "P31" in data["entities"][id]["claims"]:
                    if data["entities"][id]["claims"]["P31"][0]["mainsnak"]["datavalue"]["value"]["id"] == "Q12140":
                        print(term)
                        medicamentos.add(term.lower())
                else:
                    break
            except:
                break

    with open("medicamentos.txt", "wb") as f:
        for med in medicamentos:
            f.write(med.encode("UTF-8") + "\n".encode("UTF-8"))

def freeTextSearch(search):
    searchURL=baseURL+"wbsearchentities&search="+urllib.parse.quote_plus(search)+"&language=en&format=json"
    data=urllib.request.urlopen(searchURL).read()
    data=json.loads(data)
    return data["search"]


if __name__ == '__main__':
    main()