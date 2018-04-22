#!/bin/bash
#####################################################################################
##! @SCRIPTNAME        : myjar.sh
##! @FUNCTION          : 将本工程jar包与依赖jar包重新封装成一个独立jar包
##! @AUTHOR            : hetq
##! @DATE              : 2017-11-21 17:38:15
##! @MODIFY            : 无
#####################################################################################

if [ $# -lt 1 ];then
    echo -e "Usage: $0 version [new|update]"
    echo -e "- update: [default] only update project jar"
    echo -e "- new   : update depend libs"
    exit 1
fi

version=$1
if [ x$2 = x ];then
    action="update"
else
    action=$2
fi

start=`date "+%s"`

mkdir -p _tmp
if [ x$action = xnew ];then
    if [ ! -f smart-gdp-server-${version}-bin.zip ];then
        echo "smart-gdp-server-${version}-bin.zip not found"
        exit 1
    fi
    rm -rf _tmp/lib/
    rm -rf _tmp/classes/
    echo "unzip smart-gdp-server-${version}-bin.zip -d _tmp/"
    unzip -o smart-gdp-server-${version}-bin.zip -d _tmp/ > /dev/null
    rm -f smart-gdp-server-${version}.jar;cp -f _tmp/smart-gdp-server-${version}.jar .
    echo "unzip jars in lib"
    find _tmp/lib -type f -name '*.jar' -exec unzip -o {} -d _tmp/classes/ \; > /dev/null
fi

if [ ! -f smart-gdp-server-${version}.jar ];then
    echo "smart-gdp-server-${version}.jar not found"
    exit 1
fi
echo "unzip smart-gdp-server-${version}.jar -d _tmp/classes/"
unzip -o smart-gdp-server-${version}.jar -d _tmp/classes/ > /dev/null
echo "jar -cf smart-gdp-server-${version}-with-dependency.jar -C _tmp/classes/ ."
rm -rf smart-gdp-server-${version}-with-dependency.jar
# solve the problem:java.lang.SecurityException: Invalid signature file digest for Manifest
rm -rf _tmp/classes/META-INF/*.RSA _tmp/classes/META-INF/*.DSA _tmp/classes/META-INF/*.SF
jar -cf smart-gdp-server-${version}-with-dependency.jar -C _tmp/classes/ . > /dev/null

end=`date "+%s"`
let usetime=(end-start)
echo "usetime:${usetime}s"
