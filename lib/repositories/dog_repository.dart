import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/dog_image.dart';

class DogRepository {
  final String baseUrl = 'https://dog.ceo/api/breeds/image/random';

  Future<DogImage> getRandomDogImage() async {
    try {
      final response = await http.get(Uri.parse(baseUrl));
      
      if (response.statusCode == 200) {
        return DogImage.fromJson(json.decode(response.body));
      } else {
        throw Exception('Failed to load dog image');
      }
    } catch (e) {
      throw Exception('Failed to connect to the API');
    }
  }
} 