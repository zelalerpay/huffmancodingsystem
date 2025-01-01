import java.io.*;
import java.util.*;

public class Main {

    private static Map<Byte, String> huffmanCodes = new HashMap<>();

    public static void main(String[] args) {
        // 1. Find the characters and their frequencies from letter.txt
        Map<Byte, Integer> charFrequencies = readCharFrequencies("C:\\Users\\90534\\Desktop\\letter.txt");

        // 2. Construct the Huffman tree using this alphabet (characters)
        ByteNode huffmanRoot = buildHuffmanTree(charFrequencies);

        // 3. Find the code of every character and display them
        displayHuffmanCodes(huffmanRoot);
        encodeAndWrite("C:\\Users\\90534\\Desktop\\letter.txt", "C:\\Users\\90534\\Desktop\\encode.txt", huffmanRoot);
        decodeAndWrite("C:\\Users\\90534\\Desktop\\encode.txt", "C:\\Users\\90534\\Desktop\\decode.txt", huffmanRoot);
    }

    private static Map<Byte, Integer> readCharFrequencies(String filePath) {
        Map<Byte, Integer> charFrequencies = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                byte b = (byte) ch;
                charFrequencies.put(b, charFrequencies.getOrDefault(b, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return charFrequencies;
    }

    private static ByteNode buildHuffmanTree(Map<Byte, Integer> charFrequencies) {
        MinPriorityQueue<ByteNode> nodes = new MinPriorityQueue<>();
        for (Map.Entry<Byte, Integer> entry : charFrequencies.entrySet()) {
            nodes.add(new ByteNode(entry.getKey(), entry.getValue()));
        }
        while (nodes.len() > 1) {
            ByteNode left = nodes.poll();
            ByteNode right = nodes.poll();
            ByteNode parent = new ByteNode(null, left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            nodes.add(parent);
        }
        return nodes.poll();
    }

    private static void displayHuffmanCodes(ByteNode root) {
        if (root == null) return;
        generateHuffmanCodes(root, new StringBuilder());
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            char character = (char) entry.getKey().byteValue();
            System.out.println(character + ": " + entry.getValue());
        }
    }




    private static void generateHuffmanCodes(ByteNode node, StringBuilder sb) {
        if (node != null) {
            if (node.data == null) {
                generateHuffmanCodes(node.left, new StringBuilder(sb).append("0"));
                generateHuffmanCodes(node.right, new StringBuilder(sb).append("1"));
            } else {
                huffmanCodes.put(node.data, sb.toString());
            }
        }
    }


    private static void encodeAndWrite(String sourceFilePath, String encodedFilePath, ByteNode huffmanRoot) {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(encodedFilePath))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                byte b = (byte) ch;
                String code = huffmanCodes.get(b);
                writer.write(code);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void decodeAndWrite(String encodedFilePath, String decodedFilePath, ByteNode huffmanRoot) {
        try (BufferedReader reader = new BufferedReader(new FileReader(encodedFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(decodedFilePath))) {
            ByteNode current = huffmanRoot;
            int ch;
            while ((ch = reader.read()) != -1) {
                char bit = (char) ch;
                if (bit == '0') {
                    current = current.left;
                } else if (bit == '1') {
                    current = current.right;
                }

                if (current.data != null) {
                    writer.write((char) current.data.byteValue());
                    current = huffmanRoot; //
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }


