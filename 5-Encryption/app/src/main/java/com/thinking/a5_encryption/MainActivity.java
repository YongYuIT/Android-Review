package com.thinking.a5_encryption;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TestTools.do_test();

        /*
        String test_key = "testtesttesttesttesttesttesttesttest";
        String out = EncrypTools.doEnDeCryp(1, test_key, "aaa", "hello fuck word hello fuck word");
        Log.i("yuyong", "eny-->" + out);
        String de_out = EncrypTools.doEnDeCryp(2, test_key, "aaa", out);
        Log.i("yuyong", "dey-->" + de_out);
        */


        String txt = " OpenSSL 1.1.1-pre5 (beta) 17 Apr 2018\n" +
                "\n" +
                " Copyright (c) 1998-2018 The OpenSSL Project\n" +
                " Copyright (c) 1995-1998 Eric A. Young, Tim J. Hudson\n" +
                " All rights reserved.\n" +
                "\n" +
                " DESCRIPTION\n" +
                " -----------\n" +
                "\n" +
                " The OpenSSL Project is a collaborative effort to develop a robust,\n" +
                " commercial-grade, fully featured, and Open Source toolkit implementing the\n" +
                " Transport Layer Security (TLS) protocols (including SSLv3) as well as a\n" +
                " full-strength general purpose cryptographic library.\n" +
                "\n" +
                " OpenSSL is descended from the SSLeay library developed by Eric A. Young\n" +
                " and Tim J. Hudson.  The OpenSSL toolkit is licensed under a dual-license (the\n" +
                " OpenSSL license plus the SSLeay license), which means that you are free to\n" +
                " get and use it for commercial and non-commercial purposes as long as you\n" +
                " fulfill the conditions of both licenses.\n" +
                "\n" +
                " OVERVIEW\n" +
                " --------\n" +
                "\n" +
                " The OpenSSL toolkit includes:\n" +
                "\n" +
                " libssl (with platform specific naming):\n" +
                "     Provides the client and server-side implementations for SSLv3 and TLS.\n" +
                "\n" +
                " libcrypto (with platform specific naming):\n" +
                "     Provides general cryptographic and X.509 support needed by SSL/TLS but\n" +
                "     not logically part of it.\n" +
                "\n" +
                " openssl:\n" +
                "     A command line tool that can be used for:\n" +
                "        Creation of key parameters\n" +
                "        Creation of X.509 certificates, CSRs and CRLs\n" +
                "        Calculation of message digests\n" +
                "        Encryption and decryption\n" +
                "        SSL/TLS client and server tests\n" +
                "        Handling of S/MIME signed or encrypted mail\n" +
                "        And more...\n" +
                "\n" +
                " INSTALLATION\n" +
                " ------------\n" +
                "\n" +
                " See the appropriate file:\n" +
                "        INSTALL         Linux, Unix, Windows, OpenVMS, ...\n" +
                "        NOTES.*         INSTALL addendums for different platforms\n" +
                "\n" +
                " SUPPORT\n" +
                " -------\n" +
                "\n" +
                " See the OpenSSL website www.openssl.org for details on how to obtain\n" +
                " commercial technical support. Free community support is available through the\n" +
                " openssl-users email list (see\n" +
                " https://www.openssl.org/community/mailinglists.html for further details).\n" +
                "\n" +
                " If you have any problems with OpenSSL then please take the following steps\n" +
                " first:\n" +
                "\n" +
                "    - Download the latest version from the repository\n" +
                "      to see if the problem has already been addressed\n" +
                "    - Configure with no-asm\n" +
                "    - Remove compiler optimization flags\n" +
                "\n" +
                " If you wish to report a bug then please include the following information\n" +
                " and create an issue on GitHub:\n" +
                "\n" +
                "    - OpenSSL version: output of 'openssl version -a'\n" +
                "    - Configuration data: output of 'perl configdata.pm --dump'\n" +
                "    - OS Name, Version, Hardware platform\n" +
                "    - Compiler Details (name, version)\n" +
                "    - Application Details (name, version)\n" +
                "    - Problem Description (steps that will reproduce the problem, if known)\n" +
                "    - Stack Traceback (if the application dumps core)\n" +
                "\n" +
                " Just because something doesn't work the way you expect does not mean it\n" +
                " is necessarily a bug in OpenSSL. Use the openssl-users email list for this type\n" +
                " of query.\n" +
                "\n" +
                " HOW TO CONTRIBUTE TO OpenSSL\n" +
                " ----------------------------\n" +
                "\n" +
                " See CONTRIBUTING\n" +
                "\n" +
                " LEGALITIES\n" +
                " ----------\n" +
                "\n" +
                " A number of nations restrict the use or export of cryptography. If you\n" +
                " are potentially subject to such restrictions you should seek competent\n" +
                " professional legal advice before attempting to develop or distribute\n" +
                " cryptographic code.";

        txt += txt;
        txt += txt;
        txt += txt;
        txt += txt;
        txt += txt;
        txt += txt;


        String key = "testtesttesttesttesttesttesttesttest";
        String out = EncrypTools.doEnDeCryp(1, key, "this is tag", txt);
        Log.i("yuyong", "eny-->" + out);
        String de_out = EncrypTools.doEnDeCryp(2, key, "this is tag", out);
        Log.i("yuyong", "dey-->" + de_out);

    }
}
