#!/usr/bin/env python3
# coding: utf-8
import io
import sys

sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

from triple_extraction import *
if __name__ == '__main__':
    data=sys.argv[1]
    # with open("test01.txt", 'r',encoding='UTF-8') as f:    #打开文件
    #     data = f.read()   #读取文件
    extractor = TripleExtractor()
    svos = extractor.triples_main(data)
    for n in svos:
        if svos.count(n)>1:
            svos.remove(n)
    # print('svos', svos)
    all_entities=[]
    for i in range(len(svos)):
        all_entities.append(svos[i][0])
        all_entities.append(svos[i][2])
    all_entities=list(set(all_entities))
    # result="nodes\n"
    # print("nodes")
    # for i in range(len(all_entities)):
    #     result=result+"name:"+all_entities[i]+" des:nodedes"+str(i)+" symbolSize:50 category:1\n"
    #     print("name:"+all_entities[i]+" des:nodedes"+str(i)+" symbolSize:50 category:1")
    # result+="links\n"
    # print("links")
    # for i in range(len(svos)):
    #     result = result + "source:" + svos[i][0] + " target:" + svos[i][2]
    #     result = result + " name:" + svos[i][1] + " des:link" + str(i) + "des\n"
    #     print("source:" + svos[i][0] + " target:" + svos[i][2]+" name:" + svos[i][1] + " des:link" + str(i) + "des")
    # if os.path.exists("./kg/target.json"):
    #         os.remove("./kg/target.json")
    # f = open("./kg/target.json", "w+")
    # f.write(result)
    # f.close()
    for i in range(len(all_entities)):
        print(all_entities[i])
    print("links")
    for i in range(len(svos)):
        print(svos[i][0]+" "+svos[i][2]+" "+svos[i][1])