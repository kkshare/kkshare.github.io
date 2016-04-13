---
layout: post
title: "sample code of c"
description: "sample code of c"
category: "tech"
tags: [c,sample code]

---
{% include JB/setup %}

    #ifdef _WIN32
    typedef unsigned __int64          ULONGLONG, UINT64;
    typedef __int64                         LONGLONG, INT64;
    #define get_uint64(p, u)          sscanf(p, "%I64u", &u)
    #define get_int64(p, u)               sscanf(p, "%I64d", &u)
    #define FMT64D                         "%I64d"
    #define FMT64U                         "%I64u"
    #else // Linux
    #include <stdint.h>
    typedef uint64_t                    ULONGLONG, UINT64;
    typedef int64_t                         LONGLONG, INT64;
    #define get_uint64(p, u)          sscanf(p, "%llu", &u)
    #define get_int64(p, u)               sscanf(p, "%lld", &u)
    #define FMT64D                         "%lld"
    #define FMT64U                         "%llu"
    #endif

    {
       std::string filename = argv[1];
       std::ifstream in(filename.c_str(), std::ios::in | std::ios::binary);
       if (!in) {
              fprintf(stderr, "xxx");
              return 1;
       }
       in.seekg (0, std::ios::end);
       int length = in.tellg();
       in.seekg (0, std::ios::beg);
       std::string original;
       original.resize(length);
       in.read(&original[0], length);
       in.close();
       size_t dot_pos = filename.rfind('.');
       if(dot_pos==std::string::npos)//没找到 npos=-1
    }

