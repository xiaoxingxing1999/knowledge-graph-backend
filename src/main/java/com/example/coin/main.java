package com.example.coin;

import java.io.*;
import java.util.ArrayList;
import com.example.coin.serviceImpl.KgServiceImpl;

import java.util.Collections;
import java.util.Random;

import com.google.gson.*;

class Main {
    public static ArrayList<ArrayList<String>> dic=new ArrayList<>();

    public static void main(String[] args) throws IOException {
        getAnswer();
    }
    //生成知识图谱
    public static void getKg() {
        String str2="抑郁症又叫忧郁症，是一种脑部精神疾病，并发症有焦虑、失眠和精神失常，需要心理疏导和药物治疗。";
        Process proc;
        boolean change=false;
        int numOfNodes=0;
        int numOfLinks=0;
        JsonObject jsonContainer=new JsonObject();
        jsonContainer.addProperty("title","新建知识图谱");
        JsonArray nodes=new JsonArray();
        JsonArray links=new JsonArray();
        try {
            proc = Runtime.getRuntime().exec("src\\main\\resources\\kg\\dist\\extraction.exe "+str2);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"utf-8"));
            //BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));
            String line = null;
            Random r=new Random(1);
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (line.equals("links")){
                    change=true;
                    continue;
                }
                if (!change){
                    numOfNodes++;
                    JsonObject node=new JsonObject();
                    String[] object=line.split(" ");
                    node.addProperty("name",object[1]);
                    node.addProperty("des","nodedes"+numOfNodes);

                    JsonObject frontSize=new JsonObject();
                    if (object[0].equals("0")) {
                        node.addProperty("symbol", "circle");
                        node.addProperty("symbolSize",40);
                        node.addProperty("type","highlight");
                        frontSize.addProperty("frontSize",15);
                    }
                    else if (object[0].equals("2")||object[0].equals("6")||object[0].equals("9")) {
                        node.addProperty("symbol", "triangle");
                        node.addProperty("symbolSize",30);
                        frontSize.addProperty("frontSize",12);
                    }
                    else if (object[0].equals("1")||object[0].equals("3")||object[0].equals("5")||object[0].equals("7")||object[0].equals("8")) {
                        node.addProperty("symbol", "rectangle");
                        node.addProperty("symbolSize",30);
                        frontSize.addProperty("frontSize",12);
                    }
                    else {
                        node.addProperty("symbol", "diamond");
                        node.addProperty("symbolSize",30);
                        frontSize.addProperty("frontSize",12);
                    }
                    JsonObject color=new JsonObject();
                    node.add("itemStyle",color);


                    node.add("label",frontSize);
                    if (object[0].equals("0")) {
                        node.addProperty("category",0);
                    }
                    else if (object[0].equals("1")) {
                        node.addProperty("category",1);
                    }
                    else if (object[0].equals("2")) {
                        node.addProperty("category",2);
                    }
                    else if (object[0].equals("3")) {
                        node.addProperty("category",3);
                    }
                    else if (object[0].equals("4")) {
                        node.addProperty("category",4);
                    }
                    else if (object[0].equals("5")) {
                        node.addProperty("category",5);
                    }
                    else if (object[0].equals("6")) {
                        node.addProperty("category",6);
                    }
                    else if (object[0].equals("7")) {
                        node.addProperty("category",7);
                    }
                    else if (object[0].equals("8")) {
                        node.addProperty("category",8);
                    }
                    else if (object[0].equals("9")) {
                        node.addProperty("category",9);
                    }
                    else if (object[0].equals("10")) {
                        node.addProperty("category",10);
                    }
                    else {
                        node.addProperty("category",11);
                    }
                    nodes.add(node);
                }
                else{
                    numOfLinks++;
                    JsonObject link=new JsonObject();
                    String[] object=line.split(" ");
                    link.addProperty("source",object[0]);
                    link.addProperty("target",object[1]);
                    link.addProperty("name",object[2]);
                    link.addProperty("des","link"+numOfLinks+"des");

                    links.add(link);
                }
            }
            jsonContainer.add("nodes",nodes);
            jsonContainer.add("links",links);
            jsonContainer.addProperty("isChartFixed",false);
            JsonArray potions=new JsonArray();
            jsonContainer.add("potions",potions);
            System.out.println(jsonContainer);
            //输出json文件
            String jsonString=jsonContainer.toString();
            File file=new File("src\\main\\resources\\kg\\data/target9.json");
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();

            in.close();
            int res=proc.waitFor();
            System.out.print(res);

            BufferedReader ine = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String linee = null;
            while((linee = ine.readLine()) != null){
                System.out.println(linee);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    //读取知识图谱进行融合的测试,传入两个字符串
    public static void getFusion() throws IOException {
        KgServiceImpl kgService=new KgServiceImpl();
        //读取内容
        //第一个知识图谱
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(new FileInputStream("src\\main\\resources\\kg\\data\\target1.json")));
        String strTmp1 = buffReader.readLine();
        ArrayList<ArrayList<String>> kg1_links=new ArrayList<>();
        ArrayList<ArrayList<String>> kg1_nodes=new ArrayList<>();

        //第二个知识图谱
        buffReader = new BufferedReader(new InputStreamReader(new FileInputStream("src\\main\\resources\\kg\\data\\target3.json")));
        String strTmp2 = buffReader.readLine();
        ArrayList<ArrayList<String>> kg2_links=new ArrayList<>();
        ArrayList<ArrayList<String>> kg2_nodes=new ArrayList<>();

        buffReader.close();

        JsonObject jsonContainer1=new JsonParser().parse(strTmp1).getAsJsonObject();
        JsonArray nodes1=(JsonArray) jsonContainer1.get("nodes");
        JsonArray links1= (JsonArray) jsonContainer1.get("links");

        for (int j=0;j<nodes1.size();j++){
            ArrayList<String> temp=new ArrayList<>();
            JsonObject node= (JsonObject) nodes1.get(j);
            String category=node.get("category").toString().replaceAll("\"","");
            String name=node.get("name").toString().replaceAll("\"","");
            temp.add(category);
            temp.add(name);
            kg1_nodes.add(temp);
        }
        for (int j=0;j<links1.size();j++){
            ArrayList<String> temp=new ArrayList<>();
            JsonObject link= (JsonObject) links1.get(j);
            String source=link.get("source").toString().replaceAll("\"","");
            String target=link.get("target").toString().replaceAll("\"","");
            String name=link.get("name").toString().replaceAll("\"","");
            temp.add(source);
            temp.add(target);
            temp.add(name);
            kg1_links.add(temp);
        }

        JsonObject jsonContainer2=new JsonParser().parse(strTmp2).getAsJsonObject();
        JsonArray nodes2=(JsonArray) jsonContainer2.get("nodes");
        JsonArray links2= (JsonArray) jsonContainer2.get("links");

        for (int j=0;j<nodes2.size();j++){
            ArrayList<String> temp=new ArrayList<>();
            JsonObject node= (JsonObject) nodes2.get(j);
            String category=node.get("category").toString().replaceAll("\"","");
            String name=node.get("name").toString().replaceAll("\"","");
            temp.add(category);
            temp.add(name);
            kg2_nodes.add(temp);
        }
        for (int j=0;j<links2.size();j++){
            ArrayList<String> temp=new ArrayList<>();
            JsonObject link= (JsonObject) links2.get(j);
            String source=link.get("source").toString().replaceAll("\"","");
            String target=link.get("target").toString().replaceAll("\"","");
            String name=link.get("name").toString().replaceAll("\"","");
            temp.add(source);
            temp.add(target);
            temp.add(name);
            kg2_links.add(temp);
        }

        //合并三元组
        ArrayList<ArrayList<String>> links_result= new ArrayList<>();
        links_result=(ArrayList<ArrayList<String>>) kg1_links.clone();

        for (int j=0;j<kg2_links.size();j++){
            ArrayList<String> linkB=kg2_links.get(j);  //需要合并的节点
            int index1=-1;      //记录kg1中相似的主节点序号
            int index2=-1;      //记录kg1中相同的副节点序号
            for (int i=0;i<kg1_links.size();i++){
                ArrayList<String> linkA=kg1_links.get(i);
                if (kgService.fuse(linkA.get(0),linkB.get(0))){
                    //找到主节点相似
                    index1=i;
                    if (linkA.get(1).equals(linkB.get(1))){
                        //找到副节点相同
                        index2=i;
                    }
                }
            }
            ArrayList<String> change=new ArrayList<>();

            if (index1==-1){
                //与kg1中的任何一个关系不相似
                links_result.add(linkB);
            }
            else if (index2==-1){
                //主节点相似但是没有相同的父节点
                change.add(kg1_links.get(index1).get(0));
                change.add(linkB.get(1));
                change.add(linkB.get(2));
                links_result.add(change);
            }
            else{
                //主节点相似且父节点相同
                continue;
            }

        }

        //合并节点
        ArrayList<ArrayList<String>> nodes_result= new ArrayList<>();
        nodes_result=(ArrayList<ArrayList<String>>) kg1_nodes.clone();
        for (int j=0;j<kg2_nodes.size();j++){
            boolean flag=true;
            ArrayList<String> nodeB=kg2_nodes.get(j);
            for (int i=0;i<kg1_nodes.size();i++){
                ArrayList<String> nodeA=kg1_nodes.get(i);
                if (kgService.fuse(nodeA.get(1),nodeB.get(1))){
                    //找到相似的了
                    flag=false;
                }
            }
            if (flag)
                nodes_result.add(nodeB);
        }

        //生成知识图谱
        JsonObject jsonContainer=new JsonObject();
        jsonContainer.addProperty("title","知识图谱");
        JsonArray nodes=new JsonArray();
        JsonArray links=new JsonArray();
        JsonObject frontSize=new JsonObject();
        for (int i=0;i<nodes_result.size();i++){
            JsonObject node=new JsonObject();
            node.addProperty("name",nodes_result.get(i).get(1));
            node.addProperty("des","nodedes"+(i+1));
            String category=nodes_result.get(i).get(0);
            if (category.equals("0")) {
                node.addProperty("symbol", "circle");
                node.addProperty("symbolSize",40);
                node.addProperty("type","highlight");
                frontSize.addProperty("frontSize",15);
            }
            else if (category.equals("2")||category.equals("6")||category.equals("9")) {
                node.addProperty("symbol", "triangle");
                node.addProperty("symbolSize",30);
                frontSize.addProperty("frontSize",12);
            }
            else if (category.equals("1")||category.equals("3")||category.equals("5")||category.equals("7")||category.equals("8")) {
                node.addProperty("symbol", "rectangle");
                node.addProperty("symbolSize",30);
                frontSize.addProperty("frontSize",12);
            }
            else {
                node.addProperty("symbol", "diamond");
                node.addProperty("symbolSize",30);
                frontSize.addProperty("frontSize",12);
            }
            JsonObject color=new JsonObject();
            node.add("itemStyle",color);


            node.add("label",frontSize);
            if (category.equals("0")) {
                node.addProperty("category",0);
            }
            else if (category.equals("1")) {
                node.addProperty("category",1);
            }
            else if (category.equals("2")) {
                node.addProperty("category",2);
            }
            else if (category.equals("3")) {
                node.addProperty("category",3);
            }
            else if (category.equals("4")) {
                node.addProperty("category",4);
            }
            else if (category.equals("5")) {
                node.addProperty("category",5);
            }
            else if (category.equals("6")) {
                node.addProperty("category",6);
            }
            else if (category.equals("7")) {
                node.addProperty("category",7);
            }
            else if (category.equals("8")) {
                node.addProperty("category",8);
            }
            else if (category.equals("9")) {
                node.addProperty("category",9);
            }
            else if (category.equals("10")) {
                node.addProperty("category",10);
            }
            else {
                node.addProperty("category",11);
            }
            nodes.add(node);
        }
        for (int i=0;i<links_result.size();i++){
            JsonObject link=new JsonObject();
            link.addProperty("source",links_result.get(i).get(0));
            link.addProperty("target",links_result.get(i).get(1));
            link.addProperty("name",links_result.get(i).get(2));
            link.addProperty("des","link"+(i+1)+"des");

            links.add(link);
        }
        jsonContainer.add("nodes",nodes);
        jsonContainer.add("links",links);
        jsonContainer.addProperty("isChartFixed",false);
        JsonArray potions=new JsonArray();
        jsonContainer.add("potions",potions);
        String jsonString=jsonContainer.toString();
        System.out.println(jsonString);

        File file=new File("src\\main\\resources\\kg\\data/target8.json");
        if (file.exists()){
            file.delete();
        }
        file.createNewFile();
        Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        write.write(jsonString);
        write.flush();
        write.close();
    }

    public static void getAnswer() throws IOException {
        String questions="阳痿的治愈率怎么样？";
        //1 用药
        String[] drug={"药", "药品", "用药", "胶囊", "口服液", "炎片", "吃什么药", "用什么药", "买什么药", "药片", "怎么吃药", "能吃药嘛", "能吃药吗"};
        //2 发病部位
        String[] part={"发病部位","发病位置","哪里发病","哪里有问题","哪有问题","哪儿发病",
                "哪里出问题","哪里除了问题","哪里不舒服","哪儿不舒服"};
        //3 发病人群
        String[] age={"什么时候发病","什么人发病","哪种人","谁会患","什么人","哪些人","发病人群",
                "什么时候患病","什么人患病","谁会发病", "几岁", "老了会发病嘛", "老了会发病吗", "啥时候发病", "啥时候会发病"};
        //4 传染性
        String[] infection={"传染性","传染", "会传"};
        //5 治疗时间
        String[] period={"周期", "多久", "多长时间", "多少时间", "几天", "几年", "多少天", "多少小时",
                "几个小时", "多少年", "多久能好", "痊愈", "康复","什么时候能好","治疗时间", "多久会好", "会死吗", "会死嘛", "好得了吗", "好得了嘛", "要多久"};
        //6 所属科
        String[] department={"属于什么科", "什么科", "科室", "挂什么", "挂哪个", "哪个科", "哪些科","所属科", "到哪看", "啥科"};
        //7 检查项目
        String[] check={"查什么", "检查项目", "哪些检查", "什么检查", "检查哪些", "项目", "检测什么",
                "哪些检测", "检测哪些", "化验什么", "哪些化验", "化验哪些", "哪些体检", "怎么查找",
                "如何查找", "怎么检查", "如何检查", "怎么检测", "如何检测", "查些啥"};
        //8 症状
        String[] symptom={"什么症状", "哪些症状", "症状有哪些", "症状是什么", "什么表征", "哪些表征", "表征是什么",
                "什么现象", "哪些现象", "现象有哪些", "症候", "什么表现", "哪些表现", "表现有哪些",
                "什么行为", "哪些行为", "行为有哪些", "什么状况", "哪些状况", "状况有哪些", "现象是什么",
                "表现是什么", "行为是什么", "症状", "会怎么样", "会咋样", "痛吗", "疼嘛", "痛嘛", "疼吗"};
        //9 并发症
        String[] complication={"并发症","还会患什么","还会得","一起发作","一起患","一起得", "还有其他病"};
        //10 治疗方法
        String[] treatment={"怎么办", "怎么治疗", "如何医治", "怎么医治", "怎么治", "怎么医",
                "如何治","医治方式", "疗法", "咋治", "咋办", "咋治", "治疗方法"};
        //11 治愈率
        String[] rate={"多大概率能治好", "多大几率能治好", "治好希望大么", "几率","概率" ,"几成", "比例",
                "可能性", "能治", "可治", "可以治", "可以医", "能治好吗", "可以治好吗", "会好吗",
                "能好吗", "治愈吗","治愈率"};

        //读取生成知识图谱三元组
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(new FileInputStream("src\\main\\resources\\kg\\data\\target1.json")));
        String strTmp1 = buffReader.readLine();
        ArrayList<ArrayList<String>> kg=new ArrayList<>();
        buffReader.close();
        JsonObject jsonContainer1=new JsonParser().parse(strTmp1).getAsJsonObject();
        JsonArray links= (JsonArray) jsonContainer1.get("links");
        for (int j=0;j<links.size();j++){
            ArrayList<String> temp=new ArrayList<>();
            JsonObject link= (JsonObject) links.get(j);
            String source=link.get("source").toString().replaceAll("\"","");
            String target=link.get("target").toString().replaceAll("\"","");
            String name=link.get("name").toString().replaceAll("\"","");
            temp.add(source);
            temp.add(target);
            temp.add(name);
            kg.add(temp);
        }

        //读取生成字典
        if(dic.size()==0) {
            buffReader = new BufferedReader(new InputStreamReader(new FileInputStream("src\\main\\resources\\kg\\data\\similarDic.txt")));
            String strTmp = "";
            while ((strTmp = buffReader.readLine()) != null) {
                String [] strTmpList=strTmp.split(" ");
                ArrayList<String> temp=new ArrayList<>();
                Collections.addAll(temp,strTmpList);
                dic.add(temp);
            }
        }

        //处理问题
        String[] questionList=questions.split("[?？]");
        ArrayList<ArrayList<ArrayList<String>>> transQuestions=new ArrayList<>();
        for (int i=0;i<questionList.length;i++){
            String singleQuestion=questionList[i];
            ArrayList<ArrayList<String>> singleTrans=new ArrayList<>();
            //首先去寻找主语,如果没有找到添加空
            boolean flag=false;
            for (int j=0;j<dic.size();j++){
                ArrayList<String> similarName= (ArrayList<String>) dic.get(j).clone();
                for(int k=0;k<similarName.size();k++){
                    if (singleQuestion.indexOf(similarName.get(k))!=-1){
                        //在字典的j行k列找到句子中包含的主语
                        flag=true;
                        similarName.add(String.valueOf(k));
                        singleTrans.add(similarName);
                        break;
                    }
                }
                if (flag){
                    break;
                }
                else if (j==dic.size()-1){
                    singleTrans.add(new ArrayList<String>());
                }
            }
            //1 用药
            ArrayList<String> singleDrug=new ArrayList<>();
            for(int j=0;j<drug.length;j++){
                if (singleQuestion.indexOf(drug[j])!=-1){
                    singleDrug.add(String.valueOf(1));
                    break;
                }
            }
            if (singleDrug.size()!=0)
                singleTrans.add(singleDrug);

            //2 发病部位
            ArrayList<String> singlePart=new ArrayList<>();
            for(int j=0;j<part.length;j++){
                if (singleQuestion.indexOf(part[j])!=-1){
                    singlePart.add(String.valueOf(2));
                    break;
                }
            }
            if (singlePart.size()!=0)
                singleTrans.add(singlePart);

            //3 发病人群
            ArrayList<String> singleAge=new ArrayList<>();
            for(int j=0;j<age.length;j++){
                if (singleQuestion.indexOf(age[j])!=-1){
                    singleAge.add(String.valueOf(3));
                    break;
                }
            }
            if (singleAge.size()!=0)
                singleTrans.add(singleAge);

            //4 传染性
            ArrayList<String> singleInfection=new ArrayList<>();
            for(int j=0;j<infection.length;j++){
                if (singleQuestion.indexOf(infection[j])!=-1){
                    singleInfection.add(String.valueOf(4));
                    break;
                }
            }
            if (singleInfection.size()!=0)
                singleTrans.add(singleInfection);

            //5 治疗时间
            ArrayList<String> singlePeriod=new ArrayList<>();
            for(int j=0;j<period.length;j++){
                if (singleQuestion.indexOf(period[j])!=-1){
                    singlePeriod.add(String.valueOf(5));
                    break;
                }
            }
            if (singlePeriod.size()!=0)
                singleTrans.add(singlePeriod);

            //6 所属科
            ArrayList<String> singleDepartment=new ArrayList<>();
            for(int j=0;j<department.length;j++){
                if (singleQuestion.indexOf(department[j])!=-1){
                    singleDepartment.add(String.valueOf(6));
                    break;
                }
            }
            if (singleDepartment.size()!=0)
                singleTrans.add(singleDepartment);

            //7 检查项目
            ArrayList<String> singleCheck=new ArrayList<>();
            for(int j=0;j<check.length;j++){
                if (singleQuestion.indexOf(check[j])!=-1){
                    singleCheck.add(String.valueOf(7));
                    break;
                }
            }
            if (singleCheck.size()!=0)
                singleTrans.add(singleCheck);

            //8 症状
            ArrayList<String> singleSymptom=new ArrayList<>();
            for(int j=0;j<symptom.length;j++){
                if (singleQuestion.indexOf(symptom[j])!=-1){
                    singleSymptom.add(String.valueOf(8));
                    break;
                }
            }
            if (singleSymptom.size()!=0)
                singleTrans.add(singleSymptom);

            //9 并发症
            ArrayList<String> singleComplication=new ArrayList<>();
            for(int j=0;j<complication.length;j++){
                if (singleQuestion.indexOf(complication[j])!=-1){
                    singleComplication.add(String.valueOf(9));
                    break;
                }
            }
            if (singleComplication.size()!=0)
                singleTrans.add(singleComplication);

            //10 治疗方法
            ArrayList<String> singleTreatment=new ArrayList<>();
            for(int j=0;j<treatment.length;j++){
                if (singleQuestion.indexOf(treatment[j])!=-1){
                    singleTreatment.add(String.valueOf(10));
                    break;
                }
            }
            if (singleTreatment.size()!=0)
                singleTrans.add(singleTreatment);

            //11 治愈率
            ArrayList<String> singleRate=new ArrayList<>();
            for(int j=0;j<rate.length;j++){
                if (singleQuestion.indexOf(rate[j])!=-1){
                    singleRate.add(String.valueOf(11));
                    break;
                }
            }
            if (singleRate.size()!=0)
                singleTrans.add(singleRate);

            transQuestions.add(singleTrans);
        }

        System.out.println(transQuestions);
        //根据处理好的问题生成回答
        String result="";
        ArrayList<String> lack=new ArrayList<>();
        ArrayList<String> temp=new ArrayList<>();
        for (int i=0;i<transQuestions.size();i++){
            ArrayList<ArrayList<String>> singleQuestion=transQuestions.get(i);      //第i个转换好的问题
            ArrayList<String> name=singleQuestion.get(0);

            //找到主语且有关系
            if (name.size()!=0&&singleQuestion.size()>1){
                //添加主语
                Integer indexOfSubject=Integer.parseInt(name.get(name.size()-1));
                //找到的主语不为正式名字
                if (indexOfSubject!=0){
                    result=result+name.get(indexOfSubject)+"即"+name.get(0)+"，";
                }
                else {
                    result=result+name.get(indexOfSubject)+"，";
                }

                //添加答案
                for (int j=1;j<singleQuestion.size();j++){
                    Integer indexOfRelation=Integer.parseInt(singleQuestion.get(j).get(0));
                    switch (indexOfRelation){
                        //回答药物
                        case 1:
                            temp=new ArrayList<>();
                            for (int k=0;k<kg.size();k++){
                                if (singleQuestion.get(0).contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("用药")){
                                    temp.add(kg.get(k).get(1));
                                }
                            }
                            if (temp.size()>0){
                                result=result+"治疗需要使用以下药物：";
                                for (int k=0;k<temp.size();k++){
                                    if (k==temp.size()-1){
                                        result=result+temp.get(k)+"，";
                                    }
                                    else {
                                        result=result+temp.get(k)+"、";
                                    }
                                }
                            }
                            else
                                lack.add(name.get(indexOfSubject)+"的用药");
                            break;
                        case 2:
                            temp=new ArrayList<>();
                            for (int k=0;k<kg.size();k++){
                                if (singleQuestion.get(0).contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("发病部位")){
                                    temp.add(kg.get(k).get(1));
                                }
                            }
                            if (temp.size()>0){
                                result=result+"发病部位有";
                                for (int k=0;k<temp.size();k++){
                                    if (k==temp.size()-1){
                                        result=result+temp.get(k)+"，";
                                    }
                                    else {
                                        result=result+temp.get(k)+"、";
                                    }
                                }
                            }
                            else
                                lack.add(name.get(indexOfSubject)+"的发病部位");
                            break;
                        case 3:
                            temp=new ArrayList<>();
                            for (int k=0;k<kg.size();k++){
                                if (singleQuestion.get(0).contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("发病人群")){
                                    temp.add(kg.get(k).get(1));
                                }
                            }
                            if (temp.size()>0){
                                result=result+"发病人群有";
                                for (int k=0;k<temp.size();k++){
                                    if (k==temp.size()-1){
                                        result=result+temp.get(k)+"，";
                                    }
                                    else {
                                        result=result+temp.get(k)+"、";
                                    }
                                }
                            }
                            else
                                lack.add(name.get(indexOfSubject)+"的发病人群");
                            break;
                        case 4:
                            for (int k=0;k<kg.size();k++){
                                if (singleQuestion.get(0).contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("传染性")){
                                    result=result+kg.get(k).get(1)+"，";
                                    break;
                                }
                                if (k==kg.size()-1)
                                    lack.add(name.get(indexOfSubject)+"的传染性");
                            }
                            break;
                        case 5:
                            for (int k=0;k<kg.size();k++){
                                if (singleQuestion.get(0).contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("治疗时间")){
                                    result=result+"治疗时间为"+kg.get(k).get(1)+"，";
                                    break;
                                }
                                if (k==kg.size()-1)
                                    lack.add(name.get(indexOfSubject)+"的治疗时间");
                            }
                            break;
                        case 6:
                            temp=new ArrayList<>();
                            for (int k=0;k<kg.size();k++){
                                if (singleQuestion.get(0).contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("所属科")){
                                    temp.add(kg.get(k).get(1));
                                }
                            }
                            if (temp.size()>0){
                                result=result+"所属科为";
                                for (int k=0;k<temp.size();k++){
                                    if (k==temp.size()-1){
                                        result=result+temp.get(k)+"，";
                                    }
                                    else {
                                        result=result+temp.get(k)+"、";
                                    }
                                }
                            }
                            else
                                lack.add(name.get(indexOfSubject)+"的所属科");
                            break;
                        case 7:
                            temp=new ArrayList<>();
                            for (int k=0;k<kg.size();k++){
                                if (singleQuestion.get(0).contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("检查项目")){
                                    temp.add(kg.get(k).get(1));
                                }
                            }
                            if (temp.size()>0){
                                result=result+"需要检查以下项目：";
                                for (int k=0;k<temp.size();k++){
                                    if (k==temp.size()-1){
                                        result=result+temp.get(k)+"，";
                                    }
                                    else {
                                        result=result+temp.get(k)+"、";
                                    }
                                }
                            }
                            else
                                lack.add(name.get(indexOfSubject)+"的检查项目");
                            break;
                        case 8:
                            temp=new ArrayList<>();
                            for (int k=0;k<kg.size();k++){
                                if (singleQuestion.get(0).contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("症状")){
                                    temp.add(kg.get(k).get(1));
                                }
                            }
                            if (temp.size()>0){
                                result=result+"有以下症状：";
                                for (int k=0;k<temp.size();k++){
                                    if (k==temp.size()-1){
                                        result=result+temp.get(k)+"，";
                                    }
                                    else {
                                        result=result+temp.get(k)+"、";
                                    }
                                }
                            }
                            else
                                lack.add(name.get(indexOfSubject)+"的症状");
                            break;
                        case 9:
                            temp=new ArrayList<>();
                            for (int k=0;k<kg.size();k++){
                                if (singleQuestion.get(0).contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("并发症")){
                                    temp.add(kg.get(k).get(1));
                                }
                            }
                            if (temp.size()>0){
                                result=result+"有以下并发症：";
                                for (int k=0;k<temp.size();k++){
                                    if (k==temp.size()-1){
                                        result=result+temp.get(k)+"，";
                                    }
                                    else {
                                        result=result+temp.get(k)+"、";
                                    }
                                }
                            }
                            else
                                lack.add(name.get(indexOfSubject)+"的并发症");
                            break;
                        case 10:
                            temp=new ArrayList<>();
                            for (int k=0;k<kg.size();k++){
                                if (singleQuestion.get(0).contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("治疗方法")){
                                    temp.add(kg.get(k).get(1));
                                }
                            }
                            if (temp.size()>0){
                                result=result+"治疗方法为";
                                for (int k=0;k<temp.size();k++){
                                    if (k==temp.size()-1){
                                        result=result+temp.get(k)+"，";
                                    }
                                    else {
                                        result=result+temp.get(k)+"、";
                                    }
                                }
                            }
                            else
                                lack.add(name.get(indexOfSubject)+"的治疗方法");
                            break;
                        case 11:
                            for (int k=0;k<kg.size();k++){
                                if (singleQuestion.get(0).contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("治愈率")){
                                    result=result+"治愈率为"+kg.get(k).get(1)+"，";
                                    break;
                                }
                                if (k==kg.size()-1)
                                    lack.add(name.get(indexOfSubject)+"的治愈率");
                            }
                            break;
                    }
                }
                //如果逗号之前仅有正式名字，证明什么都没有查到，删除正式名字
                if (result.substring(result.length()-name.get(0).length()-1,result.length()-1).equals(name.get(0))){
                    if ((result.length()-name.get(0).length()-1)==0)
                        result="";
                    else
                        result=result.substring(0,result.length()-name.get(0).length()-1);
                }
                if (result.length()!=0&&result.substring(result.length()-1,result.length()).equals("，"))
                    result=result.substring(0,result.length()-1)+"。";
            }
            //没有主语有关系
            else if (name.size()==0&&singleQuestion.size()>1){
                //找到前一句话的主语
                ArrayList<String> preName=new ArrayList<>();
                if (i==0)
                    continue;
                else {
                    for (int j=i-1;j>-1;j--){
                        if (transQuestions.get(j).get(0).size()!=0){
                            preName=transQuestions.get(j).get(0);
                            break;
                        }
                    }
                }
                if (preName.size()!=0) {
                    //添加主语
                    result=result+preName.get(0);

                    for (int j=1;j<singleQuestion.size();j++){
                        Integer indexOfRelation=Integer.parseInt(singleQuestion.get(j).get(0));
                        switch (indexOfRelation){
                            //回答药物
                            case 1:
                                temp=new ArrayList<>();
                                for (int k=0;k<kg.size();k++){
                                    if (preName.contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("用药")){
                                        temp.add(kg.get(k).get(1));
                                    }
                                }
                                if (temp.size()>0){
                                    result=result+"治疗需要使用以下药物：";
                                    for (int k=0;k<temp.size();k++){
                                        if (k==temp.size()-1){
                                            result=result+temp.get(k)+"，";
                                        }
                                        else {
                                            result=result+temp.get(k)+"、";
                                        }
                                    }
                                }
                                else
                                    lack.add(preName.get(0)+"的用药");
                                break;
                            case 2:
                                temp=new ArrayList<>();
                                for (int k=0;k<kg.size();k++){
                                    if (preName.contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("发病部位")){
                                        temp.add(kg.get(k).get(1));
                                    }
                                }
                                if (temp.size()>0){
                                    result=result+"发病部位有";
                                    for (int k=0;k<temp.size();k++){
                                        if (k==temp.size()-1){
                                            result=result+temp.get(k)+"，";
                                        }
                                        else {
                                            result=result+temp.get(k)+"、";
                                        }
                                    }
                                }
                                else
                                    lack.add(preName.get(0)+"的发病部位");
                                break;
                            case 3:
                                temp=new ArrayList<>();
                                for (int k=0;k<kg.size();k++){
                                    if (preName.contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("发病人群")){
                                        temp.add(kg.get(k).get(1));
                                    }
                                }
                                if (temp.size()>0){
                                    result=result+"发病人群有";
                                    for (int k=0;k<temp.size();k++){
                                        if (k==temp.size()-1){
                                            result=result+temp.get(k)+"，";
                                        }
                                        else {
                                            result=result+temp.get(k)+"、";
                                        }
                                    }
                                }
                                else
                                    lack.add(preName.get(0)+"的发病人群");
                                break;
                            case 4:
                                for (int k=0;k<kg.size();k++){
                                    if (preName.contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("传染性")){
                                        result=result+kg.get(k).get(1)+"，";
                                        break;
                                    }
                                    if (k==kg.size()-1)
                                        lack.add(preName.get(0)+"的传染性");
                                }
                                break;
                            case 5:
                                for (int k=0;k<kg.size();k++){
                                    if (preName.contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("治疗时间")){
                                        result=result+"治疗时间为"+kg.get(k).get(1)+"，";
                                        break;
                                    }
                                    if (k==kg.size()-1)
                                        lack.add(preName.get(0)+"的治疗时间");
                                }
                                break;
                            case 6:
                                temp=new ArrayList<>();
                                for (int k=0;k<kg.size();k++){
                                    if (preName.contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("所属科")){
                                        temp.add(kg.get(k).get(1));
                                    }
                                }
                                if (temp.size()>0){
                                    result=result+"所属科为";
                                    for (int k=0;k<temp.size();k++){
                                        if (k==temp.size()-1){
                                            result=result+temp.get(k)+"，";
                                        }
                                        else {
                                            result=result+temp.get(k)+"、";
                                        }
                                    }
                                }
                                else
                                    lack.add(preName.get(0)+"的所属科");
                                break;
                            case 7:
                                temp=new ArrayList<>();
                                for (int k=0;k<kg.size();k++){
                                    if (preName.contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("检查项目")){
                                        temp.add(kg.get(k).get(1));
                                    }
                                }
                                if (temp.size()>0){
                                    result=result+"需要检查以下项目：";
                                    for (int k=0;k<temp.size();k++){
                                        if (k==temp.size()-1){
                                            result=result+temp.get(k)+"，";
                                        }
                                        else {
                                            result=result+temp.get(k)+"、";
                                        }
                                    }
                                }
                                else
                                    lack.add(preName.get(0)+"的检查项目");
                                break;
                            case 8:
                                temp=new ArrayList<>();
                                for (int k=0;k<kg.size();k++){
                                    if (preName.contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("症状")){
                                        temp.add(kg.get(k).get(1));
                                    }
                                }
                                if (temp.size()>0){
                                    result=result+"有以下症状：";
                                    for (int k=0;k<temp.size();k++){
                                        if (k==temp.size()-1){
                                            result=result+temp.get(k)+"，";
                                        }
                                        else {
                                            result=result+temp.get(k)+"、";
                                        }
                                    }
                                }
                                else
                                    lack.add(preName.get(0)+"的症状");
                                break;
                            case 9:
                                temp=new ArrayList<>();
                                for (int k=0;k<kg.size();k++){
                                    if (preName.contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("并发症")){
                                        temp.add(kg.get(k).get(1));
                                    }
                                }
                                if (temp.size()>0){
                                    result=result+"有以下并发症：";
                                    for (int k=0;k<temp.size();k++){
                                        if (k==temp.size()-1){
                                            result=result+temp.get(k)+"，";
                                        }
                                        else {
                                            result=result+temp.get(k)+"、";
                                        }
                                    }
                                }
                                else
                                    lack.add(preName.get(0)+"的并发症");
                                break;
                            case 10:
                                temp=new ArrayList<>();
                                for (int k=0;k<kg.size();k++){
                                    if (preName.contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("治疗方法")){
                                        temp.add(kg.get(k).get(1));
                                    }
                                }
                                if (temp.size()>0){
                                    result=result+"治疗方法为";
                                    for (int k=0;k<temp.size();k++){
                                        if (k==temp.size()-1){
                                            result=result+temp.get(k)+"，";
                                        }
                                        else {
                                            result=result+temp.get(k)+"、";
                                        }
                                    }
                                }
                                else
                                    lack.add(preName.get(0)+"的治疗方法");
                                break;
                            case 11:
                                for (int k=0;k<kg.size();k++){
                                    if (preName.contains(kg.get(k).get(0))&&kg.get(k).get(2).equals("治愈率")){
                                        result=result+"治愈率为"+kg.get(k).get(1)+"，";
                                        break;
                                    }
                                    if (k==kg.size()-1)
                                        lack.add(preName.get(0)+"的治愈率");
                                }
                                break;
                        }
                    }
                    if (result.substring(result.length()-1,result.length()).equals("，"))
                        result=result.substring(0,result.length()-1)+"。";
                    if (result.substring(result.length()-preName.get(0).length(),result.length()).equals(preName.get(0)))
                        result=result.substring(0,result.length()-preName.get(0).length());

                }
            }
        }
        if (lack.size()>0){
            result=result+"知识图谱中未查找到以下内容：";
            for (int i=0;i<lack.size();i++){
                result=result+lack.get(i);
                if (i!=lack.size()-1)
                    result+="、";
                else
                    result+="。";
            }
        }

        if(result.equals("")){
            result+="我不是太听得懂。。。";
        }
        System.out.println(result);
    }

}


