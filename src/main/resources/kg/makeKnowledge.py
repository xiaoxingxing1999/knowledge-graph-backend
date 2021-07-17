#该程序对输出知识图谱三元组，与java程序配合使用

import csv
import random
import re
import string

import jiagu


result=""
index1=0
index2=0
line=5000
line=5
array1=[[0] * 2 for i in range(line*280)]
array2=[[0] * 4 for i in range(line*280)]

dictlist=['所有人群','男性','女性','妇女','孕妇'
    ,'婴幼儿','婴儿','小儿','儿童','青少年','青壮年','青年','成人','成年人','成年男性','中年'
    ,'中、老年','中老年','老年人','年迈'
    ,'脑力劳动者','肥胖者','妈妈','性活跃人群','女孩']

# with open('data/disease.csv','r',encoding='ansi',newline='') as f:
with open('src\\main\\resources\\kg\\data\\disease.csv','r',encoding='ansi',newline='') as f:
    reader = csv.reader(f)
    resource = list(reader)

for i in range(1,line):
    record = resource[i]
    name = record[0].strip().strip('.')                                             #疾病名字0
    alias = record[1].strip().strip('.').replace(" ", "")                           #疾病别名0
    part=record[2].strip().strip('.')                                               #发病部位2
    age=record[3].strip().strip('.')                                                #发病人群3
    infection=record[4].strip()                                                     #传染性4
    department=record[6].strip().strip('.').replace("  "," ")                       #所属科6
    check=record[7].strip().replace(" [详细]", "").replace("暂无相关资料","")       #检查项目7
    symptom=record[8].strip().replace(" [详细]","").replace("暂无相关资料","")      #症状8
    complication=record[9].strip().strip('.').replace("暂无相关资料","")              #并发症9
    period=record[12].strip().strip('.')                                            #治疗时间5
    treatment=record[10].strip().replace(" [详细]", "").replace("暂无相关资料","")           #治疗方法10
    drug=record[11].strip().replace(" [详细]","").replace(".","").replace("暂无相关资料","") #用药1
    rate=record[13].strip().replace("暂无相关资料","")                               #治愈率11



    # 第一种知识图谱，1000种，包含疾病名字、发病部位、发病人群、传染性、所属科、检查项目
    # if(name!=''):
    #     # aliaslist = re.split(r'[,，、]', alias)
    #     # for j in range(len(aliaslist)-1,-1,-1):
    #     #     if(aliaslist[j]=='' or aliaslist[j]==' '):
    #     #         del aliaslist[j]
    #     # # name=random.choice (aliaslist)
    #     # if(len(aliaslist)>1):
    #     #     name=aliaslist[-1]
    #     # else:
    #     #     name=aliaslist[0]
    #     array1[index1][0]=0
    #     array1[index1][1]=name
    #     index1+=1
    #     #发病部位
    #     if (part != ''):
    #         partlist = re.split(r' ', part)
    #         for j in range(len(partlist)-1,-1,-1):
    #             if(partlist[j]=='' or partlist[j]==' '):
    #                 del partlist[j]
    #         if (len(partlist) > 0):
    #             for j in range(len(partlist)):
    #                 singlePart = partlist[j]
    #                 if (singlePart != ''):
    #                     array1[index1][0]=2
    #                     array1[index1][1]=singlePart
    #
    #                     array2[index2][0]=2
    #                     array2[index2][1]=name
    #                     array2[index2][2]=singlePart
    #                     array2[index2][3]= "发病部位"
    #
    #                     index1+=1
    #                     index2+=1
    #     #发病人群
    #     if (age != ''):
    #         flag = True
    #         first = True
    #         for j in range(len(dictlist)):
    #             if (dictlist[j] in age):
    #                 singleAge = dictlist[j]
    #                 array1[index1][0] = 3
    #                 array1[index1][1]=singleAge
    #
    #                 array2[index2][0] = 3
    #                 array2[index2][1]=name;
    #                 array2[index2][2]=singleAge;
    #                 array2[index2][3]= "发病人群"
    #
    #                 index1+=1
    #                 index2+=1
    #
    #     #传染性
    #     if(infection!=''):
    #         array1[index1][0] = 4
    #         array1[index1][1]=infection
    #         array2[index2][0]=4
    #         array2[index2][1]=name
    #         array2[index2][2]=infection
    #         array2[index2][3]= "传染性"
    #         index1+=1
    #         index2+=1
    #
    #     #所属科
    #     if (department != ''):
    #         departmentlist = re.split(r' ', department)
    #         for j in range(len(departmentlist) - 1, -1, -1):
    #             if (departmentlist[j][-1] != "科" or department[j]=='' or department[j]==' '):
    #                 del departmentlist[j]
    #         if (len(departmentlist) > 0):
    #             for j in range(len(departmentlist)):
    #                 singleDepartment = departmentlist[j]
    #                 if (singleDepartment != ''):
    #                     array1[index1][0] = 6
    #                     array1[index1][1]=singleDepartment
    #
    #                     array2[index2][0] = 6
    #                     array2[index2][1]=name
    #                     array2[index2][2]=singleDepartment
    #                     array2[index2][3]= "所属科"
    #
    #                     index1+=1
    #                     index2+=1
    #
    #     #检查项目
    #     if(check!= ''):
    #         checklist = re.split(r' ', check)
    #         for j in range(len(checklist)-1,-1,-1):
    #             if(checklist[j]=='' or checklist[j]==' '):
    #                 del checklist[j]
    #         if(len(checklist)>0):
    #             for j in range(len(checklist)):
    #                 singleCheck=checklist[j]
    #                 if(singleCheck!=''):
    #                     array1[index1][0]=7
    #                     array1[index1][1]=singleCheck
    #
    #                     array2[index2][0]=7
    #                     array2[index2][1]=name
    #                     array2[index2][2]=singleCheck
    #                     array2[index2][3]="检查项目"
    #
    #                     index1 += 1
    #                     index2 += 1





    # 第二种知识图谱，1000种，包含疾病别名、传染性、并发症、治疗时间、所属科、治愈率
    # if(alias!=''):
    #     aliaslist = re.split(r'[,，、]', alias)
    #     for j in range(len(aliaslist)-1,-1,-1):
    #         if(aliaslist[j]=='' or aliaslist[j]==' '):
    #             del aliaslist[j]
    #     # name=random.choice (aliaslist)
    #     if(len(aliaslist)>1):
    #         name=aliaslist[-1]
    #     else:
    #         name=aliaslist[0]
    #     array1[index1][0]=0
    #     array1[index1][1] = name
    #     index1 += 1
    #
    #     #传染性
    #     if(infection!=''):
    #         array1[index1][0] = 4
    #         array1[index1][1]=infection
    #         array2[index2][0]=4
    #         array2[index2][1]=name
    #         array2[index2][2]=infection
    #         array2[index2][3]= "传染性"
    #         index1+=1
    #         index2+=1
    #
    #     #并发症
    #     if(complication!=''):
    #         complicationlist = re.split(r' ', complication)
    #         for j in range(len(complicationlist) - 1, -1, -1):
    #             if (complicationlist[j]=='[详细]' or complicationlist[j]=='' or complicationlist[j]==' '):
    #                 del complicationlist[j]
    #         if (len(complicationlist)>0):
    #             for j in range(len(complicationlist)):
    #                 singleComplication = complicationlist[j]
    #                 if (singleComplication != ''):
    #                     array1[index1][0] = 9
    #                     array1[index1][1]=singleComplication
    #                     array2[index2][0] = 9
    #                     array2[index2][1] = name
    #                     array2[index2][2] = singleComplication
    #                     array2[index2][3] = "并发症"
    #                     index1+=1
    #                     index2+=1
    #
    #     #治疗时间
    #     if(period!=''):
    #         array1[index1][0] = 5
    #         array1[index1][1]=period
    #         array2[index2][0] = 5
    #         array2[index2][1] = name
    #         array2[index2][2] = period
    #         array2[index2][3] = "治疗时间"
    #         index1+=1
    #         index2+=1
    #
    #     #所属科
    #     if (department != ''):
    #         departmentlist = re.split(r' ', department)
    #         for j in range(len(departmentlist) - 1, -1, -1):
    #             if (departmentlist[j][-1] != "科" or departmentlist[j]=='' or departmentlist[j]==' '):
    #                 del departmentlist[j]
    #         if (len(departmentlist) > 0):
    #             for j in range(len(departmentlist)):
    #                 singleDepartment = departmentlist[j]
    #                 if (singleDepartment != ''):
    #                     array1[index1][0] = 6
    #                     array1[index1][1]=singleDepartment
    #
    #                     array2[index2][0] = 6
    #                     array2[index2][1]=name
    #                     array2[index2][2]=singleDepartment
    #                     array2[index2][3]= "所属科"
    #
    #                     index1+=1
    #                     index2+=1
    #
    #     #治愈率
    #     if (rate != ''):
    #         array1[index1][0] = 11
    #         array1[index1][1] = rate
    #
    #         array2[index2][0] = 11
    #         array2[index2][1] = name
    #         array2[index2][2] = rate
    #         array2[index2][3] = "治愈率"
    #
    #         index1 += 1
    #         index2 += 1


    # 第三种知识图谱，1000种，包含疾病别名、发病部位、并发症、症状、治疗方法、用药
    if (alias != ''):
        aliaslist = re.split(r'[,，、]', alias)
        for j in range(len(aliaslist)-1,-1,-1):
            if(aliaslist[j]=='' or aliaslist[j]==' '):
                del aliaslist[j]
        # name=random.choice (aliaslist)
        if (len(aliaslist) > 1):
            name = aliaslist[-1]
        else:
            name = aliaslist[0]
        array1[index1][0] = 0
        array1[index1][1] = name
        index1 += 1

        #发病部位
        if (part != ''):
            partlist = re.split(r' ', part)
            for j in range(len(partlist)-1,-1,-1):
                if(partlist[j]=='' or partlist[j]==' '):
                    del partlist[j]
            if (len(partlist) > 0):
                for j in range(len(partlist)):
                    singlePart = partlist[j]
                    if (singlePart != ''):
                        array1[index1][0]=2
                        array1[index1][1]=singlePart

                        array2[index2][0]=2
                        array2[index2][1]=name
                        array2[index2][2]=singlePart
                        array2[index2][3]= "发病部位"

                        index1+=1
                        index2+=1

        # 并发症
        if (complication != ''):
            complicationlist = re.split(r' ', complication)
            for j in range(len(complicationlist) - 1, -1, -1):
                if (complicationlist[j] == '[详细]' or complicationlist[j]=='' or complicationlist[j]==' '):
                    del complicationlist[j]
            if (len(complicationlist) > 0):
                for j in range(len(complicationlist)):
                    singleComplication = complicationlist[j]
                    if (singleComplication != ''):
                        array1[index1][0] = 9
                        array1[index1][1] = singleComplication
                        array2[index2][0] = 9
                        array2[index2][1] = name
                        array2[index2][2] = singleComplication
                        array2[index2][3] = "并发症"
                        index1 += 1
                        index2 += 1

        # 症状
        if(symptom!=''):
            symptomlist=re.split(r' ',symptom)
            for j in range(len(symptomlist)-1,-1,-1):
                if(symptomlist[j]=='' or symptomlist[j]==' '):
                    del symptomlist[j]
            if(len(symptomlist)>0):
                for j in range(len(symptomlist)):
                    singleSymptom=symptomlist[j]
                    if(singleSymptom!=''):
                        array1[index1][0] = 8
                        array1[index1][1] = singleSymptom
                        array2[index2][0] = 8
                        array2[index2][1] = name
                        array2[index2][2] = singleSymptom
                        array2[index2][3] = "症状"
                        index1 += 1
                        index2 += 1

        # 治疗方法
        if(treatment!=''):
            treatmentlist=re.split(r'[,，、 ]',treatment)
            for j in range(len(treatmentlist)-1,-1,-1):
                if(treatmentlist[j]=='' or treatmentlist[j]==' '):
                    del treatmentlist[j]
            if(len(treatmentlist)>0):
                for j in range(len(treatmentlist)):
                    singleTreatment=treatmentlist[j]
                    if(singleTreatment!=''):
                        array1[index1][0] = 10
                        array1[index1][1] = singleTreatment
                        array2[index2][0] = 10
                        array2[index2][1] = name
                        array2[index2][2] = singleTreatment
                        array2[index2][3] = "治疗方法"
                        index1 += 1
                        index2 += 1

        # 用药
        if(drug!=''):
            druglist=re.split(r' ',drug)
            for j in range(len(druglist)-1,-1,-1):
                if(druglist[j]=='' or druglist[j]==' '):
                    del druglist[j]
            for j in range(len(druglist)-1,0,-1):
                for k in range(j-1,-1,-1):
                    if(druglist[j]==druglist[k]):
                        del druglist[j]
                        break
            if (len(druglist) > 0):
                for j in range(len(druglist)):
                    singleDrug = druglist[j]
                    if (singleDrug != ''):
                        array1[index1][0] = 1
                        array1[index1][1] = singleDrug
                        array2[index2][0] = 1
                        array2[index2][1] = name
                        array2[index2][2] = singleDrug
                        array2[index2][3] = "用药"
                        index1 += 1
                        index2 += 1



    #第四种知识图谱，包含所有的，用于问答
    # if(name!=''):
    #     array1[index1][0]=0
    #     array1[index1][1]=name
    #     index1+=1
    #     #发病部位
    #     if (part != ''):
    #         partlist = re.split(r' ', part)
    #         for j in range(len(partlist)-1,-1,-1):
    #             if(partlist[j]=='' or partlist[j]==' '):
    #                 del partlist[j]
    #         if (len(partlist) > 0):
    #             for j in range(len(partlist)):
    #                 singlePart = partlist[j]
    #                 if (singlePart != ''):
    #                     array1[index1][0]=2
    #                     array1[index1][1]=singlePart
    #
    #                     array2[index2][0]=2
    #                     array2[index2][1]=name
    #                     array2[index2][2]=singlePart
    #                     array2[index2][3]= "发病部位"
    #
    #                     index1+=1
    #                     index2+=1
    #     #发病人群
    #     if (age != ''):
    #         flag = True
    #         first = True
    #         for j in range(len(dictlist)):
    #             if (dictlist[j] in age):
    #                 singleAge = dictlist[j]
    #                 array1[index1][0] = 3
    #                 array1[index1][1]=singleAge
    #
    #                 array2[index2][0] = 3
    #                 array2[index2][1]=name;
    #                 array2[index2][2]=singleAge;
    #                 array2[index2][3]= "发病人群"
    #
    #                 index1+=1
    #                 index2+=1
    #
    #     #传染性
    #     if(infection!=''):
    #         array1[index1][0] = 4
    #         array1[index1][1]=infection
    #         array2[index2][0]=4
    #         array2[index2][1]=name
    #         array2[index2][2]=infection
    #         array2[index2][3]= "传染性"
    #         index1+=1
    #         index2+=1
    #
    #     #所属科
    #     if (department != ''):
    #         departmentlist = re.split(r' ', department)
    #         for j in range(len(departmentlist) - 1, -1, -1):
    #             if (departmentlist[j][-1] != "科" or department[j]=='' or department[j]==' '):
    #                 del departmentlist[j]
    #         if (len(departmentlist) > 0):
    #             for j in range(len(departmentlist)):
    #                 singleDepartment = departmentlist[j]
    #                 if (singleDepartment != ''):
    #                     array1[index1][0] = 6
    #                     array1[index1][1]=singleDepartment
    #
    #                     array2[index2][0] = 6
    #                     array2[index2][1]=name
    #                     array2[index2][2]=singleDepartment
    #                     array2[index2][3]= "所属科"
    #
    #                     index1+=1
    #                     index2+=1
    #
    #     #检查项目
    #     if(check!= ''):
    #         checklist = re.split(r' ', check)
    #         for j in range(len(checklist)-1,-1,-1):
    #             if(checklist[j]=='' or checklist[j]==' '):
    #                 del checklist[j]
    #         if(len(checklist)>0):
    #             for j in range(len(checklist)):
    #                 singleCheck=checklist[j]
    #                 if(singleCheck!=''):
    #                     array1[index1][0]=7
    #                     array1[index1][1]=singleCheck
    #
    #                     array2[index2][0]=7
    #                     array2[index2][1]=name
    #                     array2[index2][2]=singleCheck
    #                     array2[index2][3]="检查项目"
    #
    #                     index1 += 1
    #                     index2 += 1
    #
    #     #并发症
    #     if(complication!=''):
    #         complicationlist = re.split(r' ', complication)
    #         for j in range(len(complicationlist) - 1, -1, -1):
    #             if (complicationlist[j]=='[详细]' or complicationlist[j]=='' or complicationlist[j]==' '):
    #                 del complicationlist[j]
    #         if (len(complicationlist)>0):
    #             for j in range(len(complicationlist)):
    #                 singleComplication = complicationlist[j]
    #                 if (singleComplication != ''):
    #                     array1[index1][0] = 9
    #                     array1[index1][1]=singleComplication
    #                     array2[index2][0] = 9
    #                     array2[index2][1] = name
    #                     array2[index2][2] = singleComplication
    #                     array2[index2][3] = "并发症"
    #                     index1+=1
    #                     index2+=1
    #
    #     #治疗时间
    #     if(period!=''):
    #         array1[index1][0] = 5
    #         array1[index1][1]=period
    #         array2[index2][0] = 5
    #         array2[index2][1] = name
    #         array2[index2][2] = period
    #         array2[index2][3] = "治疗时间"
    #         index1+=1
    #         index2+=1
    #
    #
    #     #治愈率
    #     if (rate != ''):
    #         array1[index1][0] = 11
    #         array1[index1][1] = rate
    #
    #         array2[index2][0] = 11
    #         array2[index2][1] = name
    #         array2[index2][2] = rate
    #         array2[index2][3] = "治愈率"
    #
    #         index1 += 1
    #         index2 += 1
    #
    #     # 症状
    #     if(symptom!=''):
    #         symptomlist=re.split(r' ',symptom)
    #         for j in range(len(symptomlist)-1,-1,-1):
    #             if(symptomlist[j]=='' or symptomlist[j]==' '):
    #                 del symptomlist[j]
    #         if(len(symptomlist)>0):
    #             for j in range(len(symptomlist)):
    #                 singleSymptom=symptomlist[j]
    #                 if(singleSymptom!=''):
    #                     array1[index1][0] = 8
    #                     array1[index1][1] = singleSymptom
    #                     array2[index2][0] = 8
    #                     array2[index2][1] = name
    #                     array2[index2][2] = singleSymptom
    #                     array2[index2][3] = "症状"
    #                     index1 += 1
    #                     index2 += 1
    #
    #     # 治疗方法
    #     if(treatment!=''):
    #         treatmentlist=re.split(r'[,，、 ]',treatment)
    #         for j in range(len(treatmentlist)-1,-1,-1):
    #             if(treatmentlist[j]=='' or treatmentlist[j]==' '):
    #                 del treatmentlist[j]
    #         if(len(treatmentlist)>0):
    #             for j in range(len(treatmentlist)):
    #                 singleTreatment=treatmentlist[j]
    #                 if(singleTreatment!=''):
    #                     array1[index1][0] = 10
    #                     array1[index1][1] = singleTreatment
    #                     array2[index2][0] = 10
    #                     array2[index2][1] = name
    #                     array2[index2][2] = singleTreatment
    #                     array2[index2][3] = "治疗方法"
    #                     index1 += 1
    #                     index2 += 1
    #
    #     # 用药
    #     if(drug!=''):
    #         druglist=re.split(r' ',drug)
    #         for j in range(len(druglist)-1,-1,-1):
    #             if(druglist[j]=='' or druglist[j]==' '):
    #                 del druglist[j]
    #         for j in range(len(druglist)-1,0,-1):
    #             for k in range(j-1,-1,-1):
    #                 if(druglist[j]==druglist[k]):
    #                     del druglist[j]
    #                     break
    #         if (len(druglist) > 0):
    #             for j in range(len(druglist)):
    #                 singleDrug = druglist[j]
    #                 if (singleDrug != ''):
    #                     array1[index1][0] = 1
    #                     array1[index1][1] = singleDrug
    #                     array2[index2][0] = 1
    #                     array2[index2][1] = name
    #                     array2[index2][2] = singleDrug
    #                     array2[index2][3] = "用药"
    #                     index1 += 1
    #                     index2 += 1


for i in range(line*280-1,-1,-1):
    for j in range(i-1,-1,-1):
        if(array1[i][1]==array1[j][1]):
            del array1[i]
            break
for i in range(len(array1)-1):
    print(str(array1[i][0]) + " " + str(array1[i][1]))

print("links")
for i in range(index2):
    print(str(array2[i][1]) + " " + str(array2[i][2]) + " " + str(array2[i][3]))