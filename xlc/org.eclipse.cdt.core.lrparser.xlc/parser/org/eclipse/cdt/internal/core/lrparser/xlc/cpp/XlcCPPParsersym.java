/*******************************************************************************
* Copyright (c) 2006, 2010 IBM Corporation and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*     IBM Corporation - initial API and implementation
*********************************************************************************/

// This file was generated by LPG

package org.eclipse.cdt.internal.core.lrparser.xlc.cpp;

public interface XlcCPPParsersym {
    public final static int
      TK__Complex = 24,
      TK__Imaginary = 25,
      TK_restrict = 35,
      TK_asm = 7,
      TK_auto = 38,
      TK_bool = 15,
      TK_break = 89,
      TK_case = 90,
      TK_catch = 134,
      TK_char = 16,
      TK_class = 66,
      TK_const = 33,
      TK_const_cast = 59,
      TK_continue = 91,
      TK_default = 92,
      TK_delete = 80,
      TK_do = 93,
      TK_double = 26,
      TK_dynamic_cast = 60,
      TK_else = 136,
      TK_enum = 68,
      TK_explicit = 39,
      TK_export = 94,
      TK_extern = 40,
      TK_false = 52,
      TK_float = 17,
      TK_for = 95,
      TK_friend = 41,
      TK_goto = 96,
      TK_if = 97,
      TK_inline = 42,
      TK_int = 18,
      TK_long = 19,
      TK_mutable = 43,
      TK_namespace = 72,
      TK_new = 81,
      TK_operator = 11,
      TK_private = 130,
      TK_protected = 131,
      TK_public = 132,
      TK_register = 44,
      TK_reinterpret_cast = 61,
      TK_return = 98,
      TK_short = 20,
      TK_signed = 21,
      TK_sizeof = 62,
      TK_static = 36,
      TK_static_cast = 63,
      TK_struct = 69,
      TK_switch = 99,
      TK_template = 67,
      TK_this = 53,
      TK_throw = 73,
      TK_try = 85,
      TK_true = 54,
      TK_typedef = 45,
      TK_typeid = 64,
      TK_typename = 22,
      TK_union = 70,
      TK_unsigned = 23,
      TK_using = 74,
      TK_virtual = 37,
      TK_void = 27,
      TK_volatile = 34,
      TK_wchar_t = 28,
      TK_while = 88,
      TK_integer = 55,
      TK_floating = 56,
      TK_charconst = 57,
      TK_stringlit = 46,
      TK_identifier = 1,
      TK_Completion = 4,
      TK_EndOfCompletion = 12,
      TK_Invalid = 137,
      TK_LeftBracket = 77,
      TK_LeftParen = 5,
      TK_Dot = 133,
      TK_DotStar = 105,
      TK_Arrow = 118,
      TK_ArrowStar = 103,
      TK_PlusPlus = 50,
      TK_MinusMinus = 51,
      TK_And = 14,
      TK_Star = 13,
      TK_Plus = 47,
      TK_Minus = 48,
      TK_Tilde = 10,
      TK_Bang = 58,
      TK_Slash = 106,
      TK_Percent = 107,
      TK_RightShift = 100,
      TK_LeftShift = 101,
      TK_LT = 71,
      TK_GT = 84,
      TK_LE = 108,
      TK_GE = 109,
      TK_EQ = 110,
      TK_NE = 111,
      TK_Caret = 112,
      TK_Or = 113,
      TK_AndAnd = 114,
      TK_OrOr = 115,
      TK_Question = 119,
      TK_Colon = 83,
      TK_ColonColon = 6,
      TK_DotDotDot = 102,
      TK_Assign = 86,
      TK_StarAssign = 120,
      TK_SlashAssign = 121,
      TK_PercentAssign = 122,
      TK_PlusAssign = 123,
      TK_MinusAssign = 124,
      TK_RightShiftAssign = 125,
      TK_LeftShiftAssign = 126,
      TK_AndAssign = 127,
      TK_CaretAssign = 128,
      TK_OrAssign = 129,
      TK_Comma = 78,
      TK_RightBracket = 104,
      TK_RightParen = 79,
      TK_RightBrace = 87,
      TK_SemiColon = 49,
      TK_LeftBrace = 82,
      TK_typeof = 32,
      TK___alignof__ = 65,
      TK___attribute__ = 8,
      TK___declspec = 9,
      TK_MAX = 116,
      TK_MIN = 117,
      TK_vector = 3,
      TK_pixel = 2,
      TK__Decimal32 = 29,
      TK__Decimal64 = 30,
      TK__Decimal128 = 31,
      TK___static_assert = 75,
      TK_ERROR_TOKEN = 76,
      TK_EOF_TOKEN = 135;

      public final static String orderedTerminalSymbols[] = {
                 "",
                 "identifier",
                 "pixel",
                 "vector",
                 "Completion",
                 "LeftParen",
                 "ColonColon",
                 "asm",
                 "__attribute__",
                 "__declspec",
                 "Tilde",
                 "operator",
                 "EndOfCompletion",
                 "Star",
                 "And",
                 "bool",
                 "char",
                 "float",
                 "int",
                 "long",
                 "short",
                 "signed",
                 "typename",
                 "unsigned",
                 "_Complex",
                 "_Imaginary",
                 "double",
                 "void",
                 "wchar_t",
                 "_Decimal32",
                 "_Decimal64",
                 "_Decimal128",
                 "typeof",
                 "const",
                 "volatile",
                 "restrict",
                 "static",
                 "virtual",
                 "auto",
                 "explicit",
                 "extern",
                 "friend",
                 "inline",
                 "mutable",
                 "register",
                 "typedef",
                 "stringlit",
                 "Plus",
                 "Minus",
                 "SemiColon",
                 "PlusPlus",
                 "MinusMinus",
                 "false",
                 "this",
                 "true",
                 "integer",
                 "floating",
                 "charconst",
                 "Bang",
                 "const_cast",
                 "dynamic_cast",
                 "reinterpret_cast",
                 "sizeof",
                 "static_cast",
                 "typeid",
                 "__alignof__",
                 "class",
                 "template",
                 "enum",
                 "struct",
                 "union",
                 "LT",
                 "namespace",
                 "throw",
                 "using",
                 "__static_assert",
                 "ERROR_TOKEN",
                 "LeftBracket",
                 "Comma",
                 "RightParen",
                 "delete",
                 "new",
                 "LeftBrace",
                 "Colon",
                 "GT",
                 "try",
                 "Assign",
                 "RightBrace",
                 "while",
                 "break",
                 "case",
                 "continue",
                 "default",
                 "do",
                 "export",
                 "for",
                 "goto",
                 "if",
                 "return",
                 "switch",
                 "RightShift",
                 "LeftShift",
                 "DotDotDot",
                 "ArrowStar",
                 "RightBracket",
                 "DotStar",
                 "Slash",
                 "Percent",
                 "LE",
                 "GE",
                 "EQ",
                 "NE",
                 "Caret",
                 "Or",
                 "AndAnd",
                 "OrOr",
                 "MAX",
                 "MIN",
                 "Arrow",
                 "Question",
                 "StarAssign",
                 "SlashAssign",
                 "PercentAssign",
                 "PlusAssign",
                 "MinusAssign",
                 "RightShiftAssign",
                 "LeftShiftAssign",
                 "AndAssign",
                 "CaretAssign",
                 "OrAssign",
                 "private",
                 "protected",
                 "public",
                 "Dot",
                 "catch",
                 "EOF_TOKEN",
                 "else",
                 "Invalid"
             };

    public final static boolean isValidForParser = true;
}
